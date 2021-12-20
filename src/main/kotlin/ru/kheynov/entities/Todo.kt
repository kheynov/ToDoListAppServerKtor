package ru.kheynov.entities

data class Todo(
    val id: String,
    var title: String,
    var isDone: Boolean,
    val timestamp: Int,
)