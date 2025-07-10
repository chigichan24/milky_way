package ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import usecase.GetCommentUseCase
import kotlin.random.Random
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Comment data class, now with a unique ID and initial position
data class Comment(val id: Long, val text: String, val yOffset: Float, val isSpecial: Boolean = false)

@Composable
fun RootScreen(windowState: WindowState, modifier: Modifier = Modifier) {
    val scrollingComments = remember { mutableStateListOf<Comment>() }
    val specialComments = remember { mutableStateListOf<Comment>() }
    val getCommentUseCase = remember { GetCommentUseCase() }
    val density = LocalDensity.current.density
    val screenWidthPx = windowState.size.width.value * density
    val screenHeightPx = windowState.size.height.value * density

    // Collect new comments from the use case
    LaunchedEffect(Unit) {
        getCommentUseCase.execute().collect { commentData ->
            if (commentData.isSpecial) {
                val newSpecialComment = Comment(System.nanoTime(), commentData.text, 0f, true)
                specialComments.add(newSpecialComment)
                // Remove after a delay
                launch { 
                    delay(5000L) // Display for 5 seconds
                    specialComments.remove(newSpecialComment)
                }
            } else {
                val randomY = Random.nextFloat() * (screenHeightPx - 50.dp.value * density).coerceAtLeast(0f) // Ensure positive height
                scrollingComments.add(Comment(System.nanoTime(), commentData.text, randomY))
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Use a key for each CommentItem to ensure proper recomposition and animation
        scrollingComments.forEach { comment ->
            key(comment.id) {
                CommentItem(
                    comment = comment,
                    screenWidthPx = screenWidthPx,
                    onAnimationEnd = {
                        // Remove the comment from the list when its animation finishes
                        scrollingComments.remove(comment)
                    },
                    modifier = Modifier
                )
            }
        }

        // Display special comments at the bottom center, stacked
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            specialComments.forEachIndexed { index, comment ->
                key(comment.id) {
                    SpecialCommentItem(comment = comment, modifier = Modifier)
                }
            }
        }
    }
}