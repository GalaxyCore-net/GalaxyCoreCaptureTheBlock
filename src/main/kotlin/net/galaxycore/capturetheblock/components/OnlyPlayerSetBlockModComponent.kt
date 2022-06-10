package net.galaxycore.capturetheblock.components

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class OnlyPlayerSetBlockModComponent : Listener {
    private val blocksByPlayers = HashSet<Int>()

    @EventHandler
    fun onSetBlock(event: BlockPlaceEvent) {
        blocksByPlayers += event.block.location.hashCode()
    }

    @EventHandler
    fun onBreakBlock(event: BlockPlaceEvent) {
        event.isCancelled = true
        val blockKey = event.block.location.hashCode()
        if (blocksByPlayers.contains(blockKey)) {
            blocksByPlayers.remove(blockKey)
            event.isCancelled = false
        }
    }
}