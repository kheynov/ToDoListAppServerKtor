package ru.kheynov.repository

import ru.kheynov.entities.Todo

interface TodoRepository {
    fun getAllTodos(): List<Todo>
    fun getTodo(id: Int): Todo?


}