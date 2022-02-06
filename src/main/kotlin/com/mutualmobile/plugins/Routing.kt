package com.mutualmobile.plugins

import com.mutualmobile.auth.FirebaseUser
import com.mutualmobile.auth.applicationCallFirebaseUser
import com.mutualmobile.database.models.MMPTOApproveUser
import com.mutualmobile.database.models.MMPTORequest
import com.mutualmobile.database.models.user.MMUser
import com.mutualmobile.database.models.user.MMUsers.name
import com.mutualmobile.rest.MMEndpoints
import com.mutualmobile.rest.models.ApiResponse
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

const val QUERY_PARAM: String = "query"

fun Application.configureRouting() {
    routing {
        authenticate(FIREBASE_AUTH) {
            registerUser()
            createPtoRequest()
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
        val user = MMUser.find { name like query }
        call.respond(ApiResponse(data = user, message = "Found ${user.count()}"))
    }
}

private fun Route.registerUser() {
    post(MMEndpoints.REGISTER_USER) {
        val firebaseUser = call.applicationCallFirebaseUser
        firebaseUser.email?.let {
            val user = transaction {
                MMUser.new {
                    name = firebaseUser.name
                    email = firebaseUser.email
                    pictureUrl = firebaseUser.picture
                    ptoBalance = null
                }
            }
            call.respond(ApiResponse(message = "Registered successfully", data = user))
        } ?: kotlin.run {
            call.respond(ApiResponse<String>(message = "Email missing in request"))
        }
    }
}

private fun Route.createPtoRequest() {
    post(MMEndpoints.REQUEST_PTO) {
        val firebaseUser = call.applicationCallFirebaseUser
        val request = call.receive<PTORequest>()
        request.startDate?.let {
            request.endDate?.let {
                if (hasApprovers(request, request.approveUsers)) {
                    val ptoRequest = transaction {
                        val ptoReqUUID = UUID.randomUUID()
                        val dbPtoRequest =
                            savePTORequest(ptoReqUUID, request.startDate, request.endDate, firebaseUser, request)

                        request.approveUsers!!.forEach { userApproveID ->
                            savePTOApproveUser(ptoReqUUID, userApproveID)
                        }
                        dbPtoRequest
                    }
                    call.respond(ApiResponse(message = "Request Created!", data = ptoRequest))
                } else {
                    call.respond(ApiResponse<String>(message = "Please send a list of users who will approve."))
                }
            }
        }
        call.respond(ApiResponse<String>(message = "Please check the request body and try again"))
    }
}

private fun hasApprovers(
    request: PTORequest,
    approveUsers: List<String?>?
) = request.approveUsers.isNullOrEmpty() || (approveUsers?.filterNotNull()?.size ?: 0) > 1

private fun savePTORequest(
    ptoReqUUID: UUID?,
    nstartDate: LocalDateTime,
    nendDate: LocalDateTime,
    firebaseUser: FirebaseUser,
    request: PTORequest
) = MMPTORequest.new(ptoReqUUID) {
    startDate = nstartDate
    endDate = nendDate
    applyingUserId = firebaseUser.userId!!
    requestMessage = request.requestMessage
}

private fun savePTOApproveUser(ptoReqUUID: UUID, userApproveID: String?) {
    MMPTOApproveUser.new(UUID.randomUUID()) {
        this.ptoRequestId = ptoReqUUID
        this.ptoApproveUserId = UUID.fromString(userApproveID)
    }
}
