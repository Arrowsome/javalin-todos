package com.example.todo

import java.sql.*

object Database {

    private fun connect(): Connection {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/todo",
            "todo",
            "+todo+"
        )
    }

    fun<T> run(query: String, f: PreparedStatement.() -> T): T = connect().use {
        f.invoke(it.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
    }

}

fun<T> ResultSet.map(f: (ResultSet) -> T): List<T> {
    val result = mutableListOf<T>()
    while (this.next()) {
        result += f.invoke(this)
    }
    return result
}