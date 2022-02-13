package dev.baseio.superapp.plugins

import io.ktor.html.*
import kotlinx.html.*
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureTemplating() {


    routing {
        get("/html-dsl") {
            call.respondHtml {
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                }
            }
        }
    }
}
