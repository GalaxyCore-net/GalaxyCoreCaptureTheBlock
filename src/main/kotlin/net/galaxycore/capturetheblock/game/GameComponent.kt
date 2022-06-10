package net.galaxycore.capturetheblock.game

import org.bukkit.event.Listener

interface GameComponent : Listener {
    fun enableComponent()
    fun disableComponent()
}