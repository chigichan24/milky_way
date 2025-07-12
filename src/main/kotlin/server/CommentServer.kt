package server

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.MutableSharedFlow
import model.CommentData
import server.model.CommentRequest

class CommentServer {

    private val _commentFlow = MutableSharedFlow<CommentData>()
    val commentFlow = _commentFlow

    fun start() {
        embeddedServer(CIO, port = 8080) {
            install(ContentNegotiation) {
                json(json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true })
            }
            routing {
                post("/comment") {
                    val request = call.receive<CommentRequest>()
                    _commentFlow.emit(CommentData(request.text))
                    call.respond(request)
                }
            }
        }.start(wait = false)
    }
}
