package com.example.todo

import java.sql.*

object Database {

    private fun connect(): Connection {
        return DriverManager.getConnection(
            System.getenv("DATABASE_URL")
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