package ru.kheynov.entities

import org.jetbrains.exposed.sql.Table

object Todos : Table() {
	val id = integer("id").autoIncrement().uniqueIndex()
	val title = text("title")
	val isDone = bool("isDone")
	val timestamp = integer("timestamp")
}
