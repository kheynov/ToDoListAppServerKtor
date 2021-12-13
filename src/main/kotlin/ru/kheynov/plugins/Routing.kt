package ru.kheynov.plugins

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.kheynov.entities.Todo
import ru.kheynov.repository.InMemoryTodoRepositoryImpl
import ru.kheynov.repository.TodoRepository

fun Application.configureRouting() {

    routing {
        val repository: TodoRepository = InMemoryTodoRepositoryImpl()
        get("/") {
            call.respondText("Todo Application")
        }
        get("/todos") {
            call.respond(repository.getAllTodos())
        }
        get("/todos/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Id not specified correctly")
                return@get
            }
            val todo = repository.getTodo(id)
            if (todo == null) {
                call.respond(HttpStatusCode.NotFound, "Todo not found")
            } else {
                call.respond(HttpStatusCode.OK, todo)
            }

        }
        post("/todos") {

        }
        put("/todos/{id}") {

        }
        delete("/todos/{id}") {

        }
    }
}
