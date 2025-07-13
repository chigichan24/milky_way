package server.model

import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    val text: String,
)
