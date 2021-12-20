package ru.kheynov.repository

import ru.kheynov.entities.Todo
import ru.kheynov.entities.TodoDraft

interface TodoRepository {
    fun getAllTodos(): List<Todo>

    fun getTodo(id: String): Todo?

    fun addTodo(draft: TodoDraft): Todo

    fun removeTodo(id: String)

    fun updateTodo(id: String, todoDraft: TodoDraft)

}