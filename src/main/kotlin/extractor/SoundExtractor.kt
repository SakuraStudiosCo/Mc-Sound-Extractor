package co.sakurastudios.mcsoundextractor.extractor

import co.sakurastudios.mcsoundextractor.config.MinecraftConfig
import co.sakurastudios.mcsoundextractor.model.ExtractionStats
import co.sakurastudios.mcsoundextractor.util.Logger
import co.sakurastudios.mcsoundextractor.version.VersionManager
import java.io.File
import kotlin.system.exitProcess

/**
 * Main class responsible for coordinating the sound extraction process.
 */
class SoundExtractor {
    private val config = MinecraftConfig()
    private val logger = Logger()
    private val versionManager = VersionManager(config, logger)
    private val fileExtractor = FileExtractor(config, logger)

    fun extract() {
        logger.info("Minecraft Sound Extractor v1.0.0")
        logger.info("--------------------------------")

        validatePaths()
        val versions = versionManager.getAvailableVersions()
        val selectedVersion = versionManager.promptVersion(versions)
        extractSounds(selectedVersion)
    }

    private fun validatePaths() {
        if (!File(config.assetsPath).exists()) {
            logger.error("Minecraft assets folder not found at: ${config.assetsPath}")
            exitProcess(1)
        }
    }

    private fun extractSounds(version: String) {
        val outputDir = File("minecraft_sounds_$version").apply { mkdirs() }
        logger.info("\nExtracting sounds to: ${outputDir.absolutePath}")

        try {
            val stats = fileExtractor.extractAllSounds(version, outputDir)
            reportResults(stats)
        } catch (e: Exception) {
            logger.error("Error during extraction: ${e.message}")
            exitProcess(1)
        }
    }

    private fun reportResults(stats: ExtractionStats) {
        logger.info("\nExtraction complete!")
        logger.success("Successfully extracted ${stats.extractedFiles} out of ${stats.totalFiles} sound files")
    }
}