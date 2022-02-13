package dev.baseio.superapp.koin

import dev.baseio.superapp.Config
import dev.baseio.superapp.database.DatabaseConnectionService
import dev.baseio.superapp.database.DatabaseConnectionServiceImpl
import io.ktor.config.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module
import org.ktorm.database.Database

fun databaseModule(config: ApplicationConfig): Module = module {
    single { Config(config) }
    single<DatabaseConnectionService> { DatabaseConnectionServiceImpl(get()) }
}

class DatabaseComponent : KoinComponent {
    fun provideDatabaseConnectionService(): DatabaseConnectionService = get()
    fun provideDatabaseInstance(): Database = get()
    fun provideDatabaseConfig(): Config = get()
}