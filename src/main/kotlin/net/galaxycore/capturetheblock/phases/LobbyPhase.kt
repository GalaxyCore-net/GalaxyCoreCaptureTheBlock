package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.components.CancelGameIfToLittlePlayersComponent
import net.galaxycore.capturetheblock.components.TeleportToSpawnComponent
import net.galaxycore.capturetheblock.components.NoBlockModificationComponent
import net.galaxycore.capturetheblock.components.NoHealthModificationComponent
import net.galaxycore.capturetheblock.game.Phase

class LobbyPhase : Phase() {
    override fun onEnable() {
        listenWith(
            TeleportToSpawnComponent::class.java,
            NoBlockModificationComponent::class.java,
            NoHealthModificationComponent::class.java,
        )

        // Configure the game to cancel if there are too few players
        CancelGameIfToLittlePlayersComponent.currentMinPlayers = 2
        CancelGameIfToLittlePlayersComponent.lookOnTeams = false
    }

    fun onCountdownStart() {
        PluginInstance.listenerPool.activate(mutableListOf(
                CancelGameIfToLittlePlayersComponent::class.java
            ).toTypedArray())
    }

    override fun onDisable() {

    }
}