package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.components.NoDestroyLobbyComponent
import net.galaxycore.capturetheblock.game.Phase

class LobbyPhase : Phase() {
    override fun onEnable() {
        listenWith(
            NoDestroyLobbyComponent::class.java
        )
    }

    override fun onDisable() {

    }
}