package net.galaxycore.capturetheblock.components

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.utils.i
import net.galaxycore.galaxycorecore.vanish.isVanished
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class CancelGameIfToLittlePlayersComponent : Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val currentPlayers = Bukkit.getOnlinePlayers().filter { !it.isVanished }
        if (currentPlayers.size >= currentMinPlayers) return

        i("Triggering game cancel")
        PluginInstance.game.cancel()
    }

    companion object {
        var currentMinPlayers: Int = 2
        var lookOnTeams: Boolean = false // TODO: Implement this
    }
}