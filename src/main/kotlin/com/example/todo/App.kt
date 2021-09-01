package com.example.todo

import com.example.todo.task.TaskController
import com.example.todo.user.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

fun main() {
    val app = Javalin.create { }.start(8080)
    app.get("/", controller = {
        val name = queryParam("name") ?: "Friend!"
        result("Hello, $name!")
    })
    app.routes {
        path("users") {
            post("signup", UserController::signup)
            post("login", UserController::login)
        }
        path("tasks") {
            post(TaskController::addTask)
            get(TaskController::getTasks)
            path(":task_id") {
                patch(TaskController::editTask)
                delete(TaskController::deleteTask)
            }
        }
    }

//    app.exception(Validator.ValidationError::class.java) { exception, ctx ->
//        ctx.json(exception.map).status(400)
//    }
}