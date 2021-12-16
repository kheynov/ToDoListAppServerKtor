package ru.kheynov.repository

import ru.kheynov.entities.Todo
import ru.kheynov.entities.TodoDraft

class InMemoryTodoRepositoryImpl : TodoRepository {
	private val todos = mutableListOf<Todo>()

	override fun getAllTodos(): List<Todo> {
		return todos
	}

	override fun getTodo(id: Int): Todo? {
		return todos.firstOrNull { it.id == id }
	}

	override fun addTodo(draft: TodoDraft): Todo {
		val todo = Todo(
			id = todos.size + 1,
			title = draft.title,
			isDone = draft.isDone,
			timestamp = draft.timestamp,
		)
		todos.add(todo)
		return todo
	}

	override fun removeTodo(id: Int): Boolean {
		return todos.removeIf { it.id == id }
	}

	override fun updateTodo(
		id: Int,
		todoDraft: TodoDraft,
	): Boolean {
		val todo = todos.firstOrNull { it.id == id } ?: return false

		todo.title = todoDraft.title
		todo.isDone = todoDraft.isDone
		return true

	}
}