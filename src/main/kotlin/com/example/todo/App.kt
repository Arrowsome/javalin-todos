package com.example.todo

import io.javalin.Javalin

fun main() {
    val app = Javalin.create { }.start(8080)
    app.get("/", controller =  {
        val name = queryParam("name") ?: "Friend!"
        result("Hello, $name!")
    })
}