package ru.kheynov.repository

import com.mongodb.ConnectionString
import com.mongodb.client.model.Filters
import org.litote.kmongo.KMongo
import ru.kheynov.entities.Todo
import ru.kheynov.entities.TodoDraft
import java.util.*

class MongoDatabaseRepositoryImpl : TodoRepository {

	private val client = KMongo.createClient(ConnectionString("mongodb://mongodb:27017"))
	private val database = client.getDatabase("todos")
	private val todoCollection = database.getCollection("todos",
		Todo::class.java)

	init {
		todoCollection.insertOne(Todo(
			id = UUID.randomUUID().toString(),
			title = "Test Todo",
			isDone = true,
			timestamp = 123,
		))
	}

	override fun getAllTodos(): List<Todo> {
		val todos = mutableListOf<Todo>()
		todoCollection.find().forEach {
			todos.add(it)
		}
		return todos
	}

	override fun getTodo(id: String): Todo? {
		return todoCollection.find(Filters.eq("id",
			id)).first()
	}

	override fun addTodo(draft: TodoDraft): Todo {
		val todo = Todo(
			id = UUID.randomUUID().toString(),
			title = draft.title,
			isDone = draft.isDone,
			timestamp = draft.timestamp,
		)
		todoCollection.insertOne(todo)
		return todo
	}

	override fun removeTodo(id: String) {
		todoCollection.findOneAndDelete(Filters.eq("id",
			id))
	}

	override fun updateTodo(
		id: String,
		todoDraft: TodoDraft,
	) {
		todoCollection.replaceOne(
			Filters.eq("id",
				id),
			Todo(
				id = id,
				title = todoDraft.title,
				isDone = todoDraft.isDone,
				timestamp = todoDraft.timestamp,
			)
		)

	}
}