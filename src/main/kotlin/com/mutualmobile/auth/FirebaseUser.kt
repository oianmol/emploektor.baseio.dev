package com.mutualmobile.auth

import io.ktor.auth.*

data class FirebaseUser(
  val userId: String?,
  val email: String?,
  val name: String?,
  val picture: String?
): Principal