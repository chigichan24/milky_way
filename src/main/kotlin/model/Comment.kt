package model

data class Comment(val id: Long, val text: String, val yOffset: Float, val isSpecial: Boolean = false)
