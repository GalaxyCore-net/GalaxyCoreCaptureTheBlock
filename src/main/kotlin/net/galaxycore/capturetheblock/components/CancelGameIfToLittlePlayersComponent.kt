package net.galaxycore.capturetheblock.components

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.cache.OnlinePlayerCache
import net.galaxycore.capturetheblock.teams.teamBlue
import net.galaxycore.capturetheblock.teams.teamRed
import net.galaxycore.capturetheblock.utils.i
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class CancelGameIfToLittlePlayersComponent : Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val currentPlayers = OnlinePlayerCache.instance.onlinePlayers
        if (lookOnTeams) {
            if (teamRed.players.size < 1) shutdown()
            if (teamBlue.players.size < 1) shutdown()
        }

        if (currentPlayers.size >= currentMinPlayers) return

        shutdown()
    }

    private fun shutdown() {
        i("Triggering game cancel")

        if (shouldEnd) {
            Bukkit.getServer().shutdown()
        } else PluginInstance.game.cancel()
    }

    companion object {
        var currentMinPlayers: Int = 2
        var lookOnTeams: Boolean = false // TODO: Implement this
        var shouldEnd: Boolean = false
    }
}