package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.model.Comment
import usecase.GetCommentUseCase
import kotlin.random.Random

@Composable
fun RootScreen(
    windowState: WindowState,
    getCommentUseCase: GetCommentUseCase,
    windowY: Int,
    modifier: Modifier = Modifier,
) {
    val scrollingComments = remember { mutableStateListOf<Comment>() }
    val specialComments = remember { mutableStateListOf<Comment>() }
    val density = LocalDensity.current.density
    val screenWidthPx = windowState.size.width.value * density

    // Collect new comments from the use case
    LaunchedEffect(Unit) {
        getCommentUseCase.execute().collect { commentData ->
            if (commentData.isSpecial) {
                val newSpecialComment = Comment(System.nanoTime(), commentData.text, 0f, true)
                specialComments.add(newSpecialComment)
                // Remove after a delay
                launch {
                    delay(10_000L) // Display for 5 seconds
                    specialComments.remove(newSpecialComment)
                }
            } else {
                val randomY = Random.nextFloat() * (windowY - 50.dp.value * density).coerceAtLeast(0f) // Ensure positive height
                scrollingComments.add(Comment(System.nanoTime(), commentData.text, randomY))
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        CommentList(
            comments = scrollingComments,
            screenWidthPx = screenWidthPx,
            onCommentRemove = { comment ->
                scrollingComments.remove(comment)
            },
        )

        // Display special comments at the bottom center, stacked
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom,
        ) {
            specialComments.forEachIndexed { index, comment ->
                key(comment.id) {
                    SpecialCommentItem(comment = comment, modifier = Modifier)
                }
            }
        }
    }
}
