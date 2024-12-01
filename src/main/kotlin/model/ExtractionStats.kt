package co.sakurastudios.mcsoundextractor.model

/**
 * Data class to track extraction progress and results.
 */
data class ExtractionStats(
    var totalFiles: Int = 0,
    var extractedFiles: Int = 0
)
