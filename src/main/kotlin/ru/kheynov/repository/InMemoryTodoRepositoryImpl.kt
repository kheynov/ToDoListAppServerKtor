package ru.kheynov.repository

import ru.kheynov.entities.Todo
import ru.kheynov.entities.TodoDraft
import java.util.*

class InMemoryTodoRepositoryImpl : TodoRepository {
	private val todos = mutableListOf<Todo>()

	override fun getAllTodos(): List<Todo> {
		return todos
	}

	override fun getTodo(id: String): Todo? {
		return todos.firstOrNull { it.id == id }
	}

	override fun addTodo(draft: TodoDraft): Todo {
		val todo = Todo(
			id = UUID.randomUUID().toString(),
			title = draft.title,
			isDone = draft.isDone,
			timestamp = draft.timestamp,
		)
		todos.add(todo)
		return todo
	}

	override fun removeTodo(id: String) {
		todos.removeIf { it.id == id }
	}

	override fun updateTodo(
		id: String,
		todoDraft: TodoDraft,
	) {
		val todo = todos.firstOrNull { it.id == id }

		todo?.title = todoDraft.title
		todo?.isDone = todoDraft.isDone
	}
}