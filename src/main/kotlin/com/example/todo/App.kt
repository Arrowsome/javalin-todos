package com.example.todo

import com.example.todo.task.TaskController
import com.example.todo.user.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

fun main() {
    val app = Javalin.create { }.start(System.getenv("PORT").toInt())
    Database.run("CREATE TABLE IF NOT EXISTS user (id int auto_increment primary key, email varchar(50), password varchar(50));") {
        execute()
    }
    Database.run("CREATE TABLE IF NOT EXISTS task (id int auto_increment primary key, title varchar(255), user_id int);") {
        execute()
    }
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