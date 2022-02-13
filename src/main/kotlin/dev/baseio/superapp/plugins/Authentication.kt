package dev.baseio.superapp.plugins

import com.google.firebase.FirebaseApp
import dev.baseio.superapp.auth.FirebaseAdmin
import dev.baseio.superapp.auth.FirebaseUser
import dev.baseio.superapp.auth.firebase
import io.ktor.application.*
import io.ktor.auth.*

const val FIREBASE_AUTH = "auth"

fun Application.authentication() {
    val firebaseApp: FirebaseApp = FirebaseAdmin.init()
    authentication {
        firebase(FIREBASE_AUTH, firebaseApp) {
            validate { credential ->
                // return Principal using credential
                FirebaseUser(
                    credential.token.uid,
                    credential.token.email,
                    credential.token.name,
                    credential.token.picture
                )
            }
        }
    }
}