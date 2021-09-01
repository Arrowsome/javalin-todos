package com.example.todo

import io.javalin.Javalin
import io.javalin.http.Context
import io.javalin.http.HandlerType

fun Javalin.get(path: String, controller: Context.() -> Unit) {
    addHandler(HandlerType.GET, path, controller)
}

fun Context.respond(content: Any? = null, status: Int) {
    when (content) {
        is String -> result(content).status(status)
        is Any -> json(content).status(status)
        else -> status(status)
    }
}