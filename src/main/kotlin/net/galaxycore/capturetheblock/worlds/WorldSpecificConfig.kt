@file:Suppress("MemberVisibilityCanBePrivate")

package net.galaxycore.capturetheblock.worlds

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class WorldSpecificConfig(file: File) : YamlConfiguration() {
    val isEnabled: Boolean
    val environment: World.Environment
    val generator: String?
    val teamRedSpawn: Location
    val teamRedAreaMin: List<Double>
    val teamRedAreaMax: List<Double>
    val teamRedBlockPos: Location
    val teamRedShopPos: Location
    val teamRedSpawnerPos: Location
    val teamBlueSpawn: Location
    val teamBlueAreaMin: List<Double>
    val teamBlueAreaMax: List<Double>
    val teamBlueBlockPos: Location
    val teamBlueShopPos: Location
    val teamBlueSpawnerPos: Location

    init {
        load(file)
        isEnabled = getBoolean("IsCaptureTheBlock")
        environment = World.Environment.valueOf(getString("WorldEnvironment")!!)
        generator = getString("Generator")
        teamRedSpawn = parseLocation("GameConfig.Red.Spawn")
        teamRedAreaMin = getDoubleList("GameConfig.Red.Area.Min")
        teamRedAreaMax = getDoubleList("GameConfig.Red.Area.Max")
        teamRedBlockPos = parseLocation("GameConfig.Red.BlockPosition")
        teamRedShopPos = parseLocation("GameConfig.Red.ShopPosition")
        teamRedSpawnerPos = parseLocation("GameConfig.Red.SpawnerPosition")

        teamBlueSpawn = parseLocation("GameConfig.Blue.Spawn")
        teamBlueAreaMin = getDoubleList("GameConfig.Blue.Area.Min")
        teamBlueAreaMax = getDoubleList("GameConfig.Blue.Area.Max")
        teamBlueBlockPos = parseLocation("GameConfig.Blue.BlockPosition")
        teamBlueShopPos = parseLocation("GameConfig.Blue.ShopPosition")
        teamBlueSpawnerPos = parseLocation("GameConfig.Blue.SpawnerPosition")
    }

    fun parseLocation(path: String): Location {
        val loc = getIntegerList(path)
        val x = loc[0].toDouble()
        val y = loc[1].toDouble()
        val z = loc[2].toDouble()
        val yaw = loc[3]?.toFloat()
        val pitch = loc[4]?.toFloat()

        if (yaw != null && pitch != null) {
            return Location(null, x, y, z, yaw, pitch)
        }

        return Location(null, x, y, z)
    }
}
