package net.galaxycore.capturetheblock.components

import net.galaxycore.capturetheblock.cache.OnlinePlayerCache
import net.galaxycore.capturetheblock.game.game
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class StartGameIfEnoughPlayersComponent : Listener {
    @EventHandler
    fun onCacheUpdate(event: OnlinePlayerCache.OnlinePlayerUpdateEvent) {
        if (event.players.size >= CancelGameIfToLittlePlayersComponent.currentMinPlayers) {
            game.begin()
        }
    }
}