package ru.kheynov.repository

import ru.kheynov.entities.Todo
import ru.kheynov.entities.TodoDraft

interface TodoRepository {
    fun getAllTodos(): List<Todo>

    fun getTodo(id: Int): Todo?

    fun addTodo(draft: TodoDraft): Todo

    fun removeTodo(id: Int)

    fun updateTodo(id: Int, todoDraft: TodoDraft)

}