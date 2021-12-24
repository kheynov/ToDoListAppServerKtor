package ru.kheynov

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import ru.kheynov.authentication.JwtConfig
import ru.kheynov.authentication.JwtSecret
import ru.kheynov.plugins.*

fun main(args: Array<String>): Unit =
        io.ktor.server.netty.EngineMain.main(args)
val jwtConfig = JwtConfig(JwtSecret.JWT_SECRET)
@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(Authentication){
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
