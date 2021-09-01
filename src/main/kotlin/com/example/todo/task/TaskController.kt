package com.example.todo.task

import com.example.todo.Database
import com.example.todo.map
import com.example.todo.respond
import com.example.todo.user.decodeJws
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import io.javalin.http.UnauthorizedResponse

object TaskController {

    fun addTask(ctx: Context) = with(ctx) {
        // Parameters
        val title = formParam("title", String::class.java).get()
        val userId = header("Authorization")?.let {
            decodeJws(it.split(' ').last().trim()).getClaim("user_id").asInt()
        } ?: throw UnauthorizedResponse()
        // Database
        Database.run(INSERT_TODO) {
            setString(1, title)
            setInt(2, userId)
            executeUpdate() == 1
        }
        // Respond
        respond(status = 201)
    }

    fun editTask(ctx: Context) = with(ctx) {
        // Parameters
        val title = formParam("title", String::class.java).get()
        val userId = header("Authorization")?.let {
            decodeJws(it.split(' ').last().trim()).getClaim("user_id").asInt()
        } ?: throw UnauthorizedResponse()
        // Database
        val updated = Database.run(UPDATE_TODO) {
            setString(1, title)
            setInt(2, userId)
            executeUpdate() == 1
        }
        if (!updated) throw NotFoundResponse()
        // Respond
        respond(status = 200)
    }

    fun getTasks(ctx: Context) = with(ctx) {
        // Parameters
        val userId = header("Authorization")?.let {
            decodeJws(it.split(' ').last().trim()).getClaim("user_id").asInt()
        } ?: throw UnauthorizedResponse()
        // Database
        val tasks = Database.run(GET_TODOS) {
            setInt(1, userId)
            executeQuery().map { rs -> Task.fromDatabase(rs) }
        }
        // Respond
        respond(tasks, status = 200)
    }

    fun deleteTask(ctx: Context) = with(ctx) {
        // Parameters
        val userId = header("Authorization")?.let {
            decodeJws(it.split(' ').last().trim()).getClaim("user_id").asInt()
        } ?: throw UnauthorizedResponse()
        val taskId = pathParam("task_id", Int::class.java).get()
        // Database
        val deleted = Database.run(DELETE_TODO) {
            setInt(1, taskId)
            setInt(2, userId)
            executeUpdate() == 1
        }
        if (!deleted) throw NotFoundResponse()
        // Respond
        respond(status = 200)
    }

    private const val INSERT_TODO = "INSERT INTO task (title, user_id) VALUES (?, ?);"
    private const val UPDATE_TODO = "UPDATE task SET title = ? WHERE id = ? LIMIT 1;"
    private const val GET_TODOS = "SELECT * FROM task WHERE user_id = ?;"
    private const val DELETE_TODO = "DELETE FROM task WHERE id = ? AND user_id = ? LIMIT 1;"
}