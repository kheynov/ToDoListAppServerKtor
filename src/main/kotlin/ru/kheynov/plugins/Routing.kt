package ru.kheynov.plugins

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.kheynov.entities.TodoDraft
import ru.kheynov.repository.MongoDatabaseRepositoryImpl
import ru.kheynov.repository.TodoRepository

fun Application.configureRouting() {

	routing {

		val repository: TodoRepository = MongoDatabaseRepositoryImpl()
		get("/") {
			call.respondText("TODO APPLICATION")
		}
		get("/todos") {
			call.respond(repository.getAllTodos())
		}
		get("/todos/{id}") {
			val id = call.parameters["id"]
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
//			println("I GOT A POST REQUEST!1!!!")
			val todoDraft = call.receiveOrNull<TodoDraft>()
			println(todoDraft.toString())
			if (todoDraft != null) {
				val todo = repository.addTodo(todoDraft)
				call.respond(todo)
			}
			else{
				call.respond(HttpStatusCode.BadRequest)
			}
		}
		put("/todos/{id}") {
			val todoDraft = call.receive<TodoDraft>()
			val todoId = call.parameters["id"]

			if (todoId == null) {
				call.respond(HttpStatusCode.BadRequest,
					"Id must be a number")
				return@put
			}
			repository.updateTodo(todoId,
				todoDraft)
			call.respond(HttpStatusCode.OK)
		}

		delete("/todos/{id}") {
			val todoId = call.parameters["id"]

			if (todoId == null) {
				call.respond(HttpStatusCode.BadRequest,
					"Id must be a number")
				return@delete
			}
			repository.removeTodo(todoId)
			call.respond(HttpStatusCode.OK)
		}
	}
}