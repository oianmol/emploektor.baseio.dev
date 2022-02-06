package com.mutualmobile.plugins

import com.google.firebase.FirebaseApp
import com.mutualmobile.auth.FirebaseAdmin
import com.mutualmobile.auth.FirebaseUser
import com.mutualmobile.auth.firebase
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