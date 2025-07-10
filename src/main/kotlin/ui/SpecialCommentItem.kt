package ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpecialCommentItem(comment: Comment, modifier: Modifier = Modifier) {
    Text(
        text = comment.text,
        fontSize = 30.sp,
        color = Color.Red,
        modifier = modifier
            .padding(vertical = 4.dp) // Add some vertical padding for stacking
            .wrapContentSize()
    )
}
