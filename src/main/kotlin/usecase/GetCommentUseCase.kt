package usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import model.CommentData
import repository.SpecialWordRepository
import server.CommentServer

class GetCommentUseCase(
    private val commentServer: CommentServer,
    private val specialWordRepository: SpecialWordRepository,
) {
    operator fun invoke(): Flow<CommentData> =
        commentServer.commentFlow.map { text ->
            CommentData(
                text = text,
                isSpecial = specialWordRepository.isSpecial(text),
            )
        }
}
