package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.components.CancelGameIfToLittlePlayersComponent
import net.galaxycore.capturetheblock.components.NoHealthModificationComponent
import net.galaxycore.capturetheblock.game.Phase

class PreparePhase : Phase() {
    override fun onEnable() {
        listenWith(
            NoHealthModificationComponent::class.java,
            CancelGameIfToLittlePlayersComponent::class.java
        )

        // Configure the game
        CancelGameIfToLittlePlayersComponent.currentMinPlayers = 2
        CancelGameIfToLittlePlayersComponent.lookOnTeams = true
        CancelGameIfToLittlePlayersComponent.shouldEnd = true
    }

    override fun onDisable() {

    }
}