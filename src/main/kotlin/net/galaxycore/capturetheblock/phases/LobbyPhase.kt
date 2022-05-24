package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.components.NoDestroyLobbyComponent
import net.galaxycore.capturetheblock.game.GamePhaseEnum
import net.galaxycore.capturetheblock.game.Phase

class LobbyPhase : Phase(GamePhaseEnum.LOBBY) {
    override fun onEnable() {
        listenWith(
            NoDestroyLobbyComponent::class.java
        )
    }

    override fun onDisable() {

    }
}