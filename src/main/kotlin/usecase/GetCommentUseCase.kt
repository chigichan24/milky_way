package usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class CommentData(val text: String, val isSpecial: Boolean = false)

class GetCommentUseCase {
    fun execute(): Flow<CommentData> = flow {
        val comments = listOf(
            CommentData("Hello, MilkyWay!"),
            CommentData("This is a flowing comment."),
            CommentData("Compose for Desktop is cool!"),
            CommentData("Random height, right to left."),
            CommentData("Another comment here."),
            CommentData("SPECIAL_COMMENT: This is a red message!", isSpecial = true),
            CommentData("SPECIAL_COMMENT: This is a red message!!", isSpecial = true),
            CommentData("SPECIAL_COMMENT: This is a red message!!!", isSpecial = true),
            CommentData("SPECIAL_COMMENT: This is a red message!!!!", isSpecial = true),
            CommentData("SPECIAL_COMMENT: This is a red message!!!!!", isSpecial = true),
            CommentData("Yet another flowing comment."),
            CommentData("SPECIAL_COMMENT: New red message on top!", isSpecial = true)
        )
        var index = 0
        while (true) {
            emit(comments[index % comments.size])
            index++
            delay(3000L) // Emit every 3 seconds
        }
    }
}