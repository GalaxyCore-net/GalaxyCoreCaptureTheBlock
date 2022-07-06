package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.components.*
import net.galaxycore.capturetheblock.game.Phase

class GamePhase : Phase() {
    override fun enable() {
        components(
            NoHungerModificationComponent::class.java,
            CancelGameIfToLittlePlayersComponent::class.java,
            BlockComponent::class.java,
            OnlyPlayerSetBlockModComponent::class.java,
            SpawnerComponent::class.java
        )

        // Configure the game
        CancelGameIfToLittlePlayersComponent.currentMinPlayers = 2
        CancelGameIfToLittlePlayersComponent.lookOnTeams = true
        CancelGameIfToLittlePlayersComponent.shouldEnd = true
    }

    override fun disable() {

    }
}