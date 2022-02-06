package dev.baseio.slackclone.plugins

import dev.baseio.slackclone.auth.applicationCallFirebaseUser
import dev.baseio.slackclone.database.DB
import dev.baseio.slackclone.database.R2DBCResult
import dev.baseio.slackclone.database.SlackDatabase
import dev.baseio.slackclone.rest.SlackEndpoints
import dev.baseio.slackclone.rest.models.ApiResponse
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.koin.ktor.ext.inject
import reactor.kotlin.core.publisher.toFlux

fun Application.configureRouting() {
    routing {
        authenticate(FIREBASE_AUTH) {
            registerUser()
            searchUsers()
        }
        rootRoute()
        tables()
    }
}

private fun Routing.tables() {
    val slackDatabase by inject<SlackDatabase>()
    get("/tables") {
        try {
            call.respond(slackDatabase.tables())
        } finally {
            call.respondText { "Error!" }
        }
    }
}

private fun Routing.rootRoute() {
    get("/") {
        call.respond(ApiResponse<String>(message = "I am alive!"))
    }
}

private fun Route.searchUsers() {
    get(SlackEndpoints.USERS) {
        val query = call.parameters[SlackEndpoints.Query.SEARCH] ?: return@get call.respond(
            status = HttpStatusCode.BadRequest,
            ApiResponse<String>("Missing or malformed query")
        )
        call.respond(ApiResponse(data = null, message = "Found users"))
    }
}

private fun Route.registerUser() {
    post(SlackEndpoints.REGISTER_USER) {
        val firebaseUser = call.applicationCallFirebaseUser
        firebaseUser.email?.let {
            call.respond(ApiResponse(message = "Registered successfully", data = null))
        } ?: kotlin.run {
            call.respond(ApiResponse<String>(message = "Email missing in request"))
        }
    }
}

