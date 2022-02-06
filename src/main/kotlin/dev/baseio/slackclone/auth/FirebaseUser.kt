package dev.baseio.slackclone.auth

import io.ktor.auth.*

data class FirebaseUser(
  val userId: String?,
  val email: String?,
  val name: String?,
  val picture: String?
): Principal