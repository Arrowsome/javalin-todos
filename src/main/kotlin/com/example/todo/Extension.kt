package com.example.todo

import io.javalin.Javalin
import io.javalin.http.Context
import io.javalin.http.HandlerType

fun Javalin.get(path: String, controller: Context.() -> Unit) {
    addHandler(HandlerType.GET, path, controller)
}