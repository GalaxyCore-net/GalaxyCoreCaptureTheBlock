package net.galaxycore.capturetheblock.game

import net.galaxycore.capturetheblock.PluginInstance
import org.bukkit.event.Listener


abstract class Phase {
    var activated = false
    var wasLoaded = false
    var wasDeactivated = false

    fun listenWith(vararg listeners: Class<out Listener>) {
        PluginInstance.listenerPool.push(listeners)
    }

    fun onEnable() {
        enable()
        wasLoaded = true
        activated = true
    }

    fun onDisable() {
        disable()
        wasDeactivated = true
        activated = false
    }

    abstract fun enable()
    abstract fun disable()
    open fun onTick(string: String) {}
}