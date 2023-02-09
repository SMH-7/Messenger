package Core.Model

/**
 * Handles error response
 */
data class errorResponse(
    val title: String,
    val status: Int,
    val type: String,
    val details: Map<String, String>?
)