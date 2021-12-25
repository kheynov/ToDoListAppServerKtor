package ru.kheynov.entities

data class User(
	val userId: String,
	val username: String,
	val hashedPassword: String,
)