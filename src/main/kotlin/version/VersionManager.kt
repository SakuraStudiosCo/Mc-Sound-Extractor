package co.sakurastudios.mcsoundextractor.version

import co.sakurastudios.mcsoundextractor.config.MinecraftConfig
import co.sakurastudios.mcsoundextractor.util.Logger
import java.io.File
import kotlin.system.exitProcess

/**
 * Handles version-related operations.
 */
class VersionManager(
    private val config: MinecraftConfig,
    private val logger: Logger
) {
    fun getAvailableVersions(): List<String> {
        val indexFiles = File(config.indexesPath).listFiles() ?: return emptyList()
        val versions = indexFiles
            .filter { it.name.endsWith(".json") }
            .map { it.nameWithoutExtension }
            .sorted()

        if (versions.isEmpty()) {
            logger.error("No Minecraft versions found in indexes folder")
            exitProcess(1)
        }

        return versions
    }

    fun promptVersion(versions: List<String>): String {
        logger.info("\nAvailable versions:")
        versions.forEach { logger.info("- $it") }

        print("\nEnter version to extract (e.g. ${versions.last()}): ")
        val selectedVersion = readLine() ?: ""

        if (!versions.contains(selectedVersion)) {
            logger.error("Invalid version selected")
            exitProcess(1)
        }

        return selectedVersion
    }
}