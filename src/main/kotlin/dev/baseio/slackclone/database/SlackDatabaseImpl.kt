package dev.baseio.slackclone.database

import dev.baseio.slackclone.database.entity.SlackUser
import io.ktor.application.*
import io.r2dbc.pool.ConnectionPool
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

class SlackDatabaseImpl(private val connectionPool: ConnectionPool) : SlackDatabase {
    override fun initializeOnStart(application: Application) {
        application.environment.monitor.subscribe(ApplicationStarted) { application1 ->
            application1.launch {
                val defer: Mono<Int> = connectionPool.warmup()
                defer.awaitFirst() //scare with an imaginary lock
                println("Pool is hot! welcome")
                createTables()
            }
        }
    }

    private suspend fun createTables() {
        val connection = connectionPool.create().awaitSingle()//we are in the coroutine - you can wait
        connection.createStatement("create schema IF NOT EXISTS slack").execute().awaitSingle()
        connection.createStatement(
            "CREATE TABLE IF NOT EXISTS `slack.USER` ( `name`  varchar(50) ,  `email` varchar(100),  " +
                    "`picurl`  text ,  `department`  varchar(100) ,  `title`  bigint ,  " +
                    "`username`  varchar(25) NOT NULL ,  `online`  bool DEFAULT false , " +
                    "CONSTRAINT pk_user PRIMARY KEY ( `username` ) ); "
        ).execute().awaitFirst()
    }


    override suspend fun getUsers(query: String): List<SlackUser> {
        val connection = connectionPool.create().awaitSingle()//we are in the coroutine - you can wait
        try {
            val result: List<R2DBCResult> = connection.createStatement(
                "select * from `slack.USER` where username like $query ;".trimIndent()
            )
                .execute()//reactive stream will return here
                .toFlux()//which we convert to a convenient Reactor Flux
                .collectList()//which can collect a stream of events with data into one event with a list of data
                .awaitFirst()//wait until everything is assembled - we are in the coroutine.
            return result.flatMap {
                //one result can generate multiple records. How is the driver's business, we only accept the fact
                it
                    .map(DB::ofUser)//transform the data into a stream of objects
                    .toFlux()//which we convert to a convenient Reactor Flux
                    .collectList()//which can collect a stream of events with data into one event with a list of data
                    .awaitFirst()//wait until everything is assembled - we are in the coroutine.
            }
        } finally {
            connection
                .close()//reactively close the connection
                .awaitFirstOrNull()//and wait for null - we are in the coroutine.
        }
    }
}