package ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import ui.model.Comment

@Composable
fun CommentList(
    comments: SnapshotStateList<Comment>,
    screenWidthPx: Float,
    onCommentRemove: (Comment) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(comments, key = { it.id }) { comment ->
            CommentItem(
                comment = comment,
                screenWidthPx = screenWidthPx,
                onAnimationEnd = {
                    onCommentRemove(comment)
                },
            )
        }
    }
}
