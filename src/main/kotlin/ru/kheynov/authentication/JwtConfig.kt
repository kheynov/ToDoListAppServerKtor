package ru.kheynov.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.*
import io.ktor.auth.jwt.*

class JwtConfig(jwtSecret: String) {

	companion object Constants {
		private const val jwtIssuer = "ru.kheynov"
		private const val jwtRealm = "ru.kheynov.todo"

		private const val CLAIM_USERID = "userId"
		private const val CLAIM_USERNAME = "userName"
	}

	private val jwtAlgorithm = Algorithm.HMAC512(jwtSecret)
	private val jwtVerifier = JWT
		.require(jwtAlgorithm)
		.withIssuer(jwtIssuer)
		.build()

	fun generateToken(user: JwtUser): String = JWT.create()
		.withSubject("Authentication")
		.withIssuer(jwtIssuer)
		.withClaim(CLAIM_USERID,
			user.userId)
		.withClaim(CLAIM_USERNAME,
			user.userName)
		.sign(jwtAlgorithm)

	fun configureKtorFeature(config: JWTAuthenticationProvider.Configuration) = with(config) {
		verifier(jwtVerifier)
		realm = jwtRealm
		validate {
			val userId = it.payload.getClaim(CLAIM_USERID).asInt()
			val userName = it.payload.getClaim(CLAIM_USERNAME).asString()
			if (userId != null && userName != null) {
				JwtUser(userId,
					userName)
			} else {
				null
			}
		}
	}

	data class JwtUser(
		val userId: Int,
		val userName: String,
	) : Principal
}