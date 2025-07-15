package repository

object SpecialWordRepository {
    private val specialWords = setOf("error", "429", "fail", "失敗", "確認")

    fun isSpecial(text: String): Boolean = specialWords.any { text.contains(it) }
}
