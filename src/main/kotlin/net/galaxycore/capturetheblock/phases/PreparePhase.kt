package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.components.CancelGameIfToLittlePlayersComponent
import net.galaxycore.capturetheblock.components.NoHealthModificationComponent
import net.galaxycore.capturetheblock.game.Phase
import net.galaxycore.capturetheblock.worlds.currentWorld
import org.bukkit.Bukkit
import org.bukkit.GameMode

class PreparePhase : Phase() {
    override fun onEnable() {
        listenWith(
            NoHealthModificationComponent::class.java,
            CancelGameIfToLittlePlayersComponent::class.java
        )

        for (player in Bukkit.getOnlinePlayers()) {
            player.inventory.clear()
            player.gameMode = GameMode.CREATIVE
            player.teleport(currentWorld!!.spawnLocation)
        }

        // Configure the game
        CancelGameIfToLittlePlayersComponent.currentMinPlayers = 2
        CancelGameIfToLittlePlayersComponent.lookOnTeams = true
        CancelGameIfToLittlePlayersComponent.shouldEnd = true
    }

    override fun onDisable() {

    }
}