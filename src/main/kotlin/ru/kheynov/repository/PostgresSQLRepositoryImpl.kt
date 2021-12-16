package ru.kheynov.repository

import io.ktor.http.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kheynov.entities.Todo
import ru.kheynov.entities.TodoDraft
import ru.kheynov.entities.Todos
import ru.kheynov.entities.Todos.isDone
import ru.kheynov.entities.Todos.title
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

class PostgresSQLRepositoryImpl : TodoRepository {
	override fun getAllTodos(): List<Todo> {
		val todos = mutableListOf<Todo>()
		transaction {
			Todos.selectAll().map {
				todos.add(Todo(
					id = it[Todos.id],
					title = it[title],
					isDone = it[isDone],
//					timestamp = it[timestamp],
				))
			}
		}
		return todos
	}

	override fun getTodo(id: Int): Todo? {
		var result: Todo? = null
		transaction {
			val query = Todos.select { Todos.id eq id }.firstOrNull()
			if (query != null) {
				result = Todo(
					id = id,
					title = query[title],
					isDone = query[isDone],
//					timestamp = query[timestamp],
				)
			}
		}
		return result
	}

	override fun addTodo(draft: TodoDraft): Todo {
		var id = -1
		Todos.insert {
			it[title] = draft.title
			it[isDone] = draft.isDone
//			it[timestamp] = draft.timestamp
			id = it[integer("id")]
		}
		return Todo(
			id = id,
			title = draft.title,
			isDone = draft.isDone,
//			timestamp = draft.timestamp,
		)
	}

	override fun removeTodo(id: Int): Boolean {
		TODO("Not yet implemented")
	}

	override fun updateTodo(
		id: Int,
		todoDraft: TodoDraft,
	): Boolean {
		TODO("Not yet implemented")
	}
}