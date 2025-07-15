package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshots.SnapshotStateList
import ui.model.Comment

@Composable
fun CommentList(
    comments: SnapshotStateList<Comment>,
    screenWidthPx: Float,
    onCommentRemove: (Comment) -> Unit,
) {
    comments.forEach { comment ->
        key(comment.id) {
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
