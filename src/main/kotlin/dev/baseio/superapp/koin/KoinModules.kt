package dev.baseio.superapp.koin

import dev.baseio.superapp.Config
import dev.baseio.superapp.database.connection.DatabaseConnectionService
import dev.baseio.superapp.database.connection.DatabaseConnectionServiceImpl
import dev.baseio.superapp.database.dao.EmployeeDao
import dev.baseio.superapp.database.dao.EmployeeDaoImpl
import io.ktor.config.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module
import org.ktorm.database.Database

fun databaseModule(config: ApplicationConfig): Module = module {
    single { Config(config) }
    single<DatabaseConnectionService> { DatabaseConnectionServiceImpl(get()) }
    single { get<DatabaseConnectionService>().connect() }
    single { EmployeeDaoImpl(get()) }
}

class DatabaseComponent : KoinComponent {
    fun provideDatabaseConnectionService(): DatabaseConnectionService = get() // do we need this again ?
    fun provideDatabaseInstance(): Database = get()
    fun provideDatabaseConfig(): Config = get() // do we need to expose this again ?
}

class DaoComponent : KoinComponent {
    fun provideEmployeeDao(): EmployeeDao = get()
}