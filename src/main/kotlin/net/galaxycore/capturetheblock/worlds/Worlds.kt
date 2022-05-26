package net.galaxycore.capturetheblock.worlds

import java.io.File
import java.nio.file.FileSystems


private val excludedGlobs = listOf(
    ".",
    "..",
    "cache",
    "libraries",
    "logs",
    "plugins",
    "versions",
    "world",
    "world_nether",
    "world_the_end",
    ".*"
)

fun discoverWorlds(): List<File> {
    val worlds = mutableListOf<File>()
    File(".").list()?.forEach {
        val file = File(it)
        if (!file.isDirectory) return@forEach

        for (excludedGlob in excludedGlobs) {
            val matcher = FileSystems.getDefault().getPathMatcher("glob:$excludedGlob")
            if (matcher.matches(file.toPath())) return@forEach
        }

        if (WorldSpecificConfig(file.getWorldConfigFile()).isEnabled) worlds.add(file)
    }
    return worlds
}