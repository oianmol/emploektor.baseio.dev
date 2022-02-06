package com.mutualmobile.auth

import io.ktor.application.*
import io.ktor.auth.*
import javax.naming.AuthenticationException

val ApplicationCall.applicationCallFirebaseUser: FirebaseUser
    get() = principal() ?: throw AuthenticationException()