package net.galaxycore.capturetheblock.worlds

import net.galaxycore.capturetheblock.PluginInstance
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import java.io.File

fun File.getWorldConfigFile(): File{
    val worldConfigFile = File(this, "world.yml")

    if (!worldConfigFile.exists()) {
        worldConfigFile.createNewFile()
        worldConfigFile.writer().apply {
            PluginInstance.getResource("world.yml")!!.bufferedReader().use {
                write(it.readText())
            }
            flush()
            close()
        }
    }

    return worldConfigFile
}

fun File.asWorld(): World? {
    if (!this.isDirectory) throw IllegalArgumentException("File is not a directory")

    val config = WorldSpecificConfig(this.getWorldConfigFile())

    return Bukkit.createWorld(WorldCreator(this.name).apply {
        environment(config.environment)

        if (config.generator != null) {
            generator(config.generator)
        }

    })
}

var currentWorld: World? = null
var allWorlds: List<File>? = null
