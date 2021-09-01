package com.example.todo.task

import java.sql.ResultSet

data class Task(val id: Int, val title: String) {

    companion object {
        fun fromDatabase(resultSet: ResultSet): Task {
            return Task(
                id = resultSet.getObject("id") as Int,
                title = resultSet.getString("title"),
            )
        }
    }

}