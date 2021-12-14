package ru.kheynov.entities

import kotlinx.serialization.Serializable

@Serializable
data class TodoDraft(
    val title: String,
    val isDone: Boolean,
)
