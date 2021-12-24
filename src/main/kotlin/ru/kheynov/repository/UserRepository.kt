package ru.kheynov.repository

import ru.kheynov.entities.User

interface UserRepository {

	fun getUser(
		username: String,
		password: String,
	): User?
}