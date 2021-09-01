package com.example.todo.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT

fun issueJWS(userIdClaim: Int): String {
    return JWT.create().withClaim("user_id", userIdClaim).sign(algorithmHS)
}

fun decodeJws(token: String): DecodedJWT {
    return JWT.require(algorithmHS).build().verify(token)
}

private var algorithmHS = Algorithm.HMAC256(System.getenv("JWT_SECRET"))