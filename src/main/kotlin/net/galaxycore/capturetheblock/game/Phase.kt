package net.galaxycore.capturetheblock.game

import net.galaxycore.capturetheblock.PluginInstance
import org.bukkit.event.Listener


abstract class Phase(val represents: GamePhaseEnum) {
    fun listenWith(vararg listeners: Class<out Listener>) {
        PluginInstance.listenerPool.push(listeners)
    }

    abstract fun onEnable()
    abstract fun onDisable()
}