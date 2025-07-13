package usecase

import kotlinx.coroutines.flow.Flow
import model.CommentData
import server.CommentServer

class GetCommentUseCase(
    private val commentServer: CommentServer,
) {
    fun execute(): Flow<CommentData> = commentServer.commentFlow
}
