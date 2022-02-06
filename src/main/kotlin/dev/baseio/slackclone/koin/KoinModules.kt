package dev.baseio.slackclone.koin

import dev.baseio.slackclone.database.ReactiveSlackDatabase
import dev.baseio.slackclone.database.SlackDatabase
import dev.baseio.slackclone.database.SlackDatabaseImpl
import org.koin.dsl.module

val databaseModule = module {
    single { ReactiveSlackDatabase.connectionPool() }
    single<SlackDatabase> { SlackDatabaseImpl(get()) }
}