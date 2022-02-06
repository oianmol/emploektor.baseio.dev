package dev.baseio.slackclone.plugins

import dev.baseio.slackclone.auth.applicationCallFirebaseUser
import dev.baseio.slackclone.rest.MMEndpoints
import dev.baseio.slackclone.rest.models.ApiResponse
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*

const val QUERY_PARAM: String = "query"

fun Application.configureRouting() {
    routing {
        authenticate(FIREBASE_AUTH) {
            registerUser()
            searchApproveUsers()
        }
        rootRoute()

    }
}

private fun Routing.rootRoute() {
    get("/") {
        call.respond(ApiResponse<String>(message = "I am alive!"))
    }
}

private fun Route.searchApproveUsers() {
    get(MMEndpoints.SEARCH_USERS) {
        val query = call.parameters[QUERY_PARAM] ?: return@get call.respond(
            status = HttpStatusCode.BadRequest,
            ApiResponse<String>("Missing or malformed query")
        )
        call.respond(ApiResponse(data = null, message = "Found users"))
    }
}

private fun Route.registerUser() {
    post(MMEndpoints.REGISTER_USER) {
        val firebaseUser = call.applicationCallFirebaseUser
        firebaseUser.email?.let {
            call.respond(ApiResponse(message = "Registered successfully", data = null))
        } ?: kotlin.run {
            call.respond(ApiResponse<String>(message = "Email missing in request"))
        }
    }
}

