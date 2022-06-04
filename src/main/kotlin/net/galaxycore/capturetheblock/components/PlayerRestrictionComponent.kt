package net.galaxycore.capturetheblock.components

import net.galaxycore.capturetheblock.utils.d
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerRestrictionComponent : Listener {
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        if (PLAYER_RESTRICTIONS.contains(event.player)) {
            event.isCancelled = true
        }

        d("Player ${event.player.name} moved")
        d(PLAYER_RESTRICTIONS.joinToString(", "))
    }

    companion object {
        val PLAYER_RESTRICTIONS = mutableListOf<Player>()
    }
}