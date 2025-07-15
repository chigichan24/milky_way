package server

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.json.Json
import server.model.CommentRequest

class CommentServer {
    private val _commentFlow = MutableSharedFlow<String>()
    val commentFlow = _commentFlow

    fun start() {
        embeddedServer(CIO, port = 8080) {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true })
            }
            routing {
                post("/comment") {
                    val request = call.receive<CommentRequest>()
                    _commentFlow.emit(request.text)
                    call.respond(request)
                }
            }
        }.start(wait = false)
    }
}
