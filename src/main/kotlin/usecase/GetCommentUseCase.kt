package usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCommentUseCase {
    fun execute(): Flow<String> = flow {
        val comments = listOf(
            "Hello, MilkyWay!",
            "This is a flowing comment.",
            "Compose for Desktop is cool!",
            "Random height, right to left.",
            "Another comment here."
        )
        var index = 0
        while (true) {
            emit(comments[index % comments.size])
            index++
            delay(3000L) // Emit every 3 seconds
        }
    }
}
