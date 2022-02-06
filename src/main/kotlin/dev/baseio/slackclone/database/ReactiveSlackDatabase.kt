package dev.baseio.slackclone.database

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.h2.H2ConnectionOption
import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds
import kotlin.time.toJavaDuration

typealias R2DBCResult = io.r2dbc.spi.Result

/**
 * Inspired by https://blog.emptyq.net/a?ID=00004-70c7cc94-091d-49fa-95aa-bbe47d738f79
 */
object ReactiveSlackDatabase {
    @OptIn(ExperimentalTime::class)
    fun connectionPool(): ConnectionPool {
        val connectionFactory = H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                .inMemory("users")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                .build()
        )

        val poolConfig = ConnectionPoolConfiguration.builder(connectionFactory)
            .maxIdleTime(10.seconds.toJavaDuration())//here they will prompt to add @ExperimentalTime annotation
            .maxSize(20)
            .build()

        return ConnectionPool(poolConfig)
    }
}