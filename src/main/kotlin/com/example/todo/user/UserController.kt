package com.example.todo.user

import com.example.todo.Database
import com.example.todo.map
import com.example.todo.respond
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse

object UserController {

    fun signup(ctx: Context) = with(ctx) {
        // Parameters
        val email = formParam("email", String::class.java).check({ it.matches(Regex(EMAIL_REGEX)) }).get()
        val password = formParam("password", String::class.java).check({ it.length >= 8 }).get()
        // Database
        val insertedId = Database.run(INSERT_USER) {
            setString(1, email)
            setString(2, password)
            executeUpdate()
            generatedKeys.run {
                next()
                getInt(1)
            }
        }
        if (insertedId <= 0) throw BadRequestResponse()
        // Respond
        respond(issueJWS(insertedId), status = 201)
    }

    fun login(ctx: Context) = with(ctx) {
        // Parameters
        val email = formParam("email") ?: throw BadRequestResponse()
        val password = formParam("password") ?: throw BadRequestResponse()
        // Database
        val userId = Database.run(SELECT_USER_ID_BY_CREDENTIALS) {
            setString(1, email)
            setString(2, password)
            executeQuery().map { rs -> rs.getInt("id") }.firstOrNull()
        }
        if (userId == null || userId <= 0) throw UnauthorizedResponse()
        // Respond
        respond(issueJWS(userId), 200)
    }

    private const val INSERT_USER = "INSERT INTO user (email , password) VALUES (?, ?);"
    private const val SELECT_USER_ID_BY_CREDENTIALS = "SELECT id FROM user WHERE email = ? AND password = ?"
    private const val EMAIL_REGEX = "^\\S+@\\S+\\.\\S+$"
}

//fun validator(f: Validator.() -> Unit) {
//    val validator = Validator()
//    f.invoke(validator)
//    val errorMessage = validator.validate()
//    if (errorMessage != null) throw Validator.ValidationError(mapOf("parameter" to errorMessage))
//}
//
//class Validator {
//    private val fails = mutableListOf<Failure>()
//
//    fun failCheck(message: String, f: () -> Boolean) {
//        fails += Failure(message, f)
//    }
//
//    fun validate(): String? {
//        fails.forEach { fail ->
//            if (fail.f.invoke()) return fail.message
//        }
//        return null
//    }
//
//    data class ValidationError(val map: Map<String, String>) : Exception()
//
//    data class Failure(val message: String, val f: () -> Boolean)
//}




