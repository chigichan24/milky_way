package ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import usecase.GetCommentUseCase
import kotlin.random.Random
import androidx.compose.ui.graphics.Color

// Comment data class, now with a unique ID and initial position
data class Comment(val id: Long, val text: String, val yOffset: Float)

@Composable
fun RootScreen() {
    val comments = remember { mutableStateListOf<Comment>() }
    val getCommentUseCase = remember { GetCommentUseCase() }
    val density = LocalDensity.current.density
    val screenWidthPx = LocalConfiguration.current.screenWidthDp.dp.value * density
    val screenHeightPx = LocalConfiguration.current.screenHeightDp.dp.value * density

    // Collect new comments from the use case
    LaunchedEffect(Unit) {
        getCommentUseCase.execute().collect { newCommentText ->
            val randomY = Random.nextFloat() * (screenHeightPx - 50.dp.value * density).coerceAtLeast(0f) // Ensure positive height
            comments.add(Comment(System.nanoTime(), newCommentText, randomY))
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Use a key for each CommentItem to ensure proper recomposition and animation
        comments.forEach { comment ->
            key(comment.id) {
                CommentItem(
                    comment = comment,
                    screenWidthPx = screenWidthPx,
                    onAnimationEnd = {
                        // Remove the comment from the list when its animation finishes
                        comments.remove(comment)
                    }
                )
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment, screenWidthPx: Float, onAnimationEnd: () -> Unit) {
    val xOffset = remember { Animatable(screenWidthPx) }
    val textWidthEstimate = comment.text.length * 10.dp.value * LocalDensity.current.density // Rough estimate

    LaunchedEffect(comment.id) {
        xOffset.animateTo(
            targetValue = -textWidthEstimate, // Animate until it's off-screen
            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
        )
        onAnimationEnd() // Call callback when animation is complete
    }

    Text(
        text = comment.text,
        fontSize = 20.sp,
        color = Color.White,
        modifier = Modifier
            .offset(x = xOffset.value.dp, y = comment.yOffset.dp)
            .wrapContentSize()
    )
}