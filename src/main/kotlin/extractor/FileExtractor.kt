package co.sakurastudios.mcsoundextractor.extractor

import co.sakurastudios.mcsoundextractor.config.MinecraftConfig
import co.sakurastudios.mcsoundextractor.model.ExtractionStats
import co.sakurastudios.mcsoundextractor.util.Logger
import com.google.gson.JsonParser
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * Handles the actual file extraction operations.
 */
class FileExtractor(
    private val config: MinecraftConfig,
    private val logger: Logger
) {
    fun extractAllSounds(version: String, outputDir: File): ExtractionStats {
        val stats = ExtractionStats()
        val indexFile = File("${config.indexesPath}/$version.json")
        val jsonObject = JsonParser.parseReader(indexFile.reader()).asJsonObject
        val objects = jsonObject.getAsJsonObject("objects")

        objects.entrySet().forEach { (path, value) ->
            if (!path.startsWith("minecraft/sounds/")) return@forEach

            stats.totalFiles++
            val hash = value.asJsonObject.get("hash").asString
            if (extractFile(hash, path, outputDir)) {
                stats.extractedFiles++
            }
        }

        return stats
    }

    private fun extractFile(hash: String, path: String, outputDir: File): Boolean {
        val sourceFile = File("${config.objectsPath}/${hash.substring(0, 2)}/$hash")
        val targetFile = File(outputDir, path.removePrefix("minecraft/"))

        return try {
            targetFile.parentFile.mkdirs()
            Files.copy(
                sourceFile.toPath(),
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
            logger.info("Extracted: ${targetFile.name}")
            true
        } catch (e: Exception) {
            logger.error("Failed to extract: ${targetFile.name} (${e.message})")
            false
        }
    }
}
