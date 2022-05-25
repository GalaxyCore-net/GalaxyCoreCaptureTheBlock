package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.components.TeleportToSpawnComponent
import net.galaxycore.capturetheblock.components.NoBlockModificationComponent
import net.galaxycore.capturetheblock.game.Phase

class LobbyPhase : Phase() {
    override fun onEnable() {
        listenWith(
            TeleportToSpawnComponent::class.java,
            NoBlockModificationComponent::class.java
        )
    }

    override fun onDisable() {

    }
}