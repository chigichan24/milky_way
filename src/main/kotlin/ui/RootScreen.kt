package ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import usecase.GetCommentUseCase
import kotlin.random.Random
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Comment data class, now with a unique ID and initial position
data class Comment(val id: Long, val text: String, val yOffset: Float, val isSpecial: Boolean = false)

@Composable
fun RootScreen(windowState: WindowState) {
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

    Box(modifier = Modifier.fillMaxSize()) {
        // Use a key for each CommentItem to ensure proper recomposition and animation
        scrollingComments.forEach { comment ->
            key(comment.id) {
                CommentItem(
                    comment = comment,
                    screenWidthPx = screenWidthPx,
                    onAnimationEnd = {
                        // Remove the comment from the list when its animation finishes
                        scrollingComments.remove(comment)
                    }
                )
            }
        }

        // Display special comments at the bottom center, stacked
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            specialComments.forEachIndexed { index, comment ->
                key(comment.id) {
                    SpecialCommentItem(comment = comment)
                }
            }
        }
    }
}

@Composable
fun SpecialCommentItem(comment: Comment) {
    Text(
        text = comment.text,
        fontSize = 30.sp,
        color = Color.Red,
        modifier = Modifier
            .padding(vertical = 4.dp) // Add some vertical padding for stacking
            .wrapContentSize()
    )
}

@Composable
fun CommentItem(comment: Comment, screenWidthPx: Float, onAnimationEnd: () -> Unit) {
    val xOffset = remember { Animatable(screenWidthPx) }
    val textWidthEstimate = comment.text.length * 10.dp.value * LocalDensity.current.density // Rough estimate

    LaunchedEffect(comment.id) {
        xOffset.animateTo(
            targetValue = -textWidthEstimate, // Animate until it's off-screen
            animationSpec = tween(durationMillis = 10000, easing = LinearEasing)
        )
        onAnimationEnd() // Call callback when animation is complete
    }

    Text(
        text = comment.text,
        fontSize = 30.sp,
        color = Color.White,
        modifier = Modifier
            .offset(x = xOffset.value.dp, y = comment.yOffset.dp)
            .wrapContentSize()
    )
}