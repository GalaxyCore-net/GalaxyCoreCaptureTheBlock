package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.game.Phase
import org.bukkit.Bukkit

class EndPhase : Phase() {
    override fun onEnable() {

    }

    override fun onDisable() {
        Bukkit.getServer().shutdown()
    }
}