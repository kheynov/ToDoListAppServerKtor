package ru.kheynov.repository

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import ru.kheynov.entities.Todo
import ru.kheynov.entities.TodoDraft

class MongoDatabaseRepositoryImpl : TodoRepository {

	private val database: MongoDatabase
	private val todoCollection: MongoCollection<Todo>

	init {
		val pojoCodecRegistry: CodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build())
		val codecRegistry: CodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
			pojoCodecRegistry)

		val clientSettings = MongoClientSettings.builder().codecRegistry(codecRegistry)
			.applyConnectionString(ConnectionString("mongodb://mongodb:27017"))
			.build()

		val mongoClient = MongoClients.create(clientSettings)
		database = mongoClient.getDatabase("todos")
		todoCollection = database.getCollection("todos",
			Todo::class.java)
	}

	override fun getAllTodos(): List<Todo> {
		val todos = mutableListOf<Todo>()
		todoCollection.find().forEach {
			todos.add(it)
		}
		return todos
	}

	override fun getTodo(id: Int): Todo? {
		return todoCollection.find(Filters.eq("id",
			id)).first()
	}

	override fun addTodo(draft: TodoDraft): Todo {
		val todo = Todo(
			id = todoCollection.find().last().id + 1,
			title = draft.title,
			isDone = draft.isDone,
			timestamp = draft.timestamp,
		)
		todoCollection.insertOne(todo)
		return todo
	}

	override fun removeTodo(id: Int) {
		todoCollection.findOneAndDelete(Filters.eq("id",
			id)) != null
	}

	override fun updateTodo(
		id: Int,
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