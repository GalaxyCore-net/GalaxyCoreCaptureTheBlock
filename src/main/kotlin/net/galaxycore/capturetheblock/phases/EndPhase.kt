package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.game.GamePhaseEnum
import net.galaxycore.capturetheblock.game.Phase
import org.bukkit.Bukkit

class EndPhase : Phase(GamePhaseEnum.END) {
    override fun onEnable() {

    }

    override fun onDisable() {
        Bukkit.getServer().shutdown()
    }
}