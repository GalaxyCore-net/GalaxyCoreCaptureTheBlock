package net.galaxycore.capturetheblock.components

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.cache.OnlinePlayerCache
import net.galaxycore.capturetheblock.game.GameComponent
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class TeleportToSpawnComponent : GameComponent {
    private val location: Location

    init {
        val composed = PluginInstance.config["lobby.spawn"].split("@")
        val world = Bukkit.getWorld(composed[0])!!

        val pos = composed[1].split(";")
        val x = pos[0].toDouble()
        val y = pos[1].toDouble()
        val z = pos[2].toDouble()
        val yaw = pos[3].toFloat()
        val pitch = pos[4].toFloat()

        location = Location(world, x, y, z, yaw, pitch)
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.player.teleport(location)
        event.player.gameMode = GameMode.ADVENTURE
    }

    override fun enableComponent() {
        OnlinePlayerCache.instance.onlinePlayers.forEach { it.teleport(location) }
    }

    override fun disableComponent() {
    }
}