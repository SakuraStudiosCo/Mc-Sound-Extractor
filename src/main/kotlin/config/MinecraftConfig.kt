package co.sakurastudios.mcsoundextractor.config

/**
 * Configuration class holding Minecraft paths based on the operating system.
 */
class MinecraftConfig {
    val minecraftPath = when {
        System.getProperty("os.name").lowercase().contains("win") ->
            "${System.getenv("APPDATA")}\\.minecraft"
        System.getProperty("os.name").lowercase().contains("mac") ->
            "${System.getProperty("user.home")}/Library/Application Support/minecraft"
        else ->
            "${System.getProperty("user.home")}/.minecraft"
    }

    val assetsPath = "$minecraftPath/assets"
    val objectsPath = "$assetsPath/objects"
    val indexesPath = "$assetsPath/indexes"
}