package ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.model.Comment

@Composable
fun CommentItem(comment: Comment, screenWidthPx: Float, onAnimationEnd: () -> Unit, modifier: Modifier = Modifier) {
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
        modifier = modifier
            .offset(x = xOffset.value.dp, y = comment.yOffset.dp)
            .wrapContentSize()
    )
}
