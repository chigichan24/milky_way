package di

import repository.SpecialWordRepository
import server.CommentServer
import usecase.GetCommentUseCase

class AppContainer {
    private val commentServer = CommentServer()
    private val specialWordRepository = SpecialWordRepository

    val getCommentUseCase = GetCommentUseCase(commentServer, specialWordRepository)

    init {
        commentServer.start()
    }
}
