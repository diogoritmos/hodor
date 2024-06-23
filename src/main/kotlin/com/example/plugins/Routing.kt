package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val rateLimiter = RateLimiter()

    routing {
        get("/") {
            if (!rateLimiter.control("1")) {
                call.respondText("Too many requests!")
            }

            call.respondText("You are allowed to enter.")
        }
    }
}
