package ru.kheynov.repository

import ru.kheynov.entities.Todo

class InMemoryTodoRepositoryImpl : TodoRepository {
    val todos = listOf(
        Todo(
            id = 1,
            title = "Test todo #1",
            isDone = false,
        ),
        Todo(
            id = 2,
            title = "Test todo #2",
            isDone = true,
        ),
        Todo(
            id = 3,
            title = "Test todo #3",
            isDone = false,
        )
    )

    override fun getAllTodos(): List<Todo> {
        return todos
    }

    override fun getTodo(id: Int): Todo? {
        return todos.firstOrNull { it.id == id }
    }
}