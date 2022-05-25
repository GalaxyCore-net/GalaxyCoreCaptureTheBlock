package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.components.CancelGameIfToLittlePlayersComponent
import net.galaxycore.capturetheblock.components.NoHungerModificationComponent
import net.galaxycore.capturetheblock.game.Phase

class GamePhase : Phase() {
    override fun onEnable() {
        listenWith(
            NoHungerModificationComponent::class.java,
            CancelGameIfToLittlePlayersComponent::class.java
        )

        // Configure the game
        CancelGameIfToLittlePlayersComponent.currentMinPlayers = 2
        CancelGameIfToLittlePlayersComponent.lookOnTeams = true
    }

    override fun onDisable() {

    }
}