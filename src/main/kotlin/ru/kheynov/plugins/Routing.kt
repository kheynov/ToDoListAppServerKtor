package ru.kheynov.plugins

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import ru.kheynov.entities.TodoDraft
import ru.kheynov.repository.InMemoryTodoRepositoryImpl
import ru.kheynov.repository.TodoRepository

fun Application.configureRouting() {

	routing {

		Database.connect("jdbc:postgresql://database:5432/todos", driver = "org.postgresql.Driver",
			user = "admin", password = "qwertypassword")

		val repository: TodoRepository = InMemoryTodoRepositoryImpl()
		get("/") {
			call.respondText("TODO APPLICATION")
		}
		get("/todos") {
			call.respond(repository.getAllTodos())
		}
		get("/todos/{id}") {
			val id = call.parameters["id"]?.toIntOrNull()
			if (id == null) {
				call.respond(HttpStatusCode.BadRequest,
					"Id must be a number")
				return@get
			}
			val todo = repository.getTodo(id)
			if (todo == null) {
				call.respond(HttpStatusCode.NotFound,
					"Todo not found")
			} else {
				call.respond(HttpStatusCode.OK,
					todo)
			}
		}

		post("/todos") {
			val todoDraft = call.receive<TodoDraft>()
			val todo = repository.addTodo(todoDraft)
			call.respond(todo)

		}
		put("/todos/{id}") {
			val todoDraft = call.receive<TodoDraft>()
			val todoId = call.parameters["id"]?.toIntOrNull()

			if (todoId == null) {
				call.respond(HttpStatusCode.BadRequest,
					"Id must be a number")
				return@put
			}
			val updated = repository.updateTodo(todoId,
				todoDraft)
			if (updated) {
				call.respond(HttpStatusCode.OK)
			} else {
				call.respond(HttpStatusCode.NotFound,
					"Todo with id $todoId not found")
			}
		}
		delete("/todos/{id}") {
			val todoId = call.parameters["id"]?.toIntOrNull()

			if (todoId == null) {
				call.respond(HttpStatusCode.BadRequest,
					"Id must be a number")
				return@delete
			}
			val removed = repository.removeTodo(todoId)
			if (removed) {
				call.respond(HttpStatusCode.OK)
			} else {
				call.respond(HttpStatusCode.NotFound,
					"Todo with id $todoId not found")
			}
		}
	}
}
