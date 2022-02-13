package dev.baseio.superapp.plugins

import dev.baseio.superapp.auth.applicationCallFirebaseUser
import dev.baseio.superapp.rest.SuperAppEndpoints
import dev.baseio.superapp.rest.models.ApiResponse
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureRouting() {
    routing {
        authenticate(FIREBASE_AUTH) {
            registerUser()
            searchUsers()
        }
        rootRoute()
    }
}


private fun Routing.rootRoute() {
    get("/") {
        call.respond(ApiResponse<String>(message = "I am alive!"))
    }
}

private fun Route.searchUsers() {
    get(SuperAppEndpoints.USERS) {
        val query = call.parameters[SuperAppEndpoints.Query.SEARCH] ?: return@get call.respond(
            status = HttpStatusCode.BadRequest,
            ApiResponse<String>("Missing or malformed query")
        )
        call.respond(ApiResponse(data = "", message = "Found users"))
    }
}

private fun Route.registerUser() {
    post(SuperAppEndpoints.REGISTER_USER) {
        val firebaseUser = call.applicationCallFirebaseUser
        firebaseUser.email?.let {
            call.respond(ApiResponse(message = "Registered successfully", data = null))
        } ?: kotlin.run {
            call.respond(ApiResponse<String>(message = "Email missing in request"))
        }
    }
}

