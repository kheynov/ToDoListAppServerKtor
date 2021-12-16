package ru.kheynov.entities

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val id: Int,
    var title: String,
    var isDone: Boolean,
//    val timestamp: Int,
)