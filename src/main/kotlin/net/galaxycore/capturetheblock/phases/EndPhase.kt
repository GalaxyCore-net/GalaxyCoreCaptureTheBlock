package net.galaxycore.capturetheblock.phases

import net.galaxycore.capturetheblock.components.CancelGameIfToLittlePlayersComponent
import net.galaxycore.capturetheblock.components.NoHealthModificationComponent
import net.galaxycore.capturetheblock.game.Phase
import org.bukkit.Bukkit

class EndPhase : Phase() {
    override fun enable() {
        listenWith(
            NoHealthModificationComponent::class.java,
            CancelGameIfToLittlePlayersComponent::class.java
        )

        // Configure the game
        CancelGameIfToLittlePlayersComponent.currentMinPlayers = 0
        CancelGameIfToLittlePlayersComponent.lookOnTeams = false
        CancelGameIfToLittlePlayersComponent.shouldEnd = false
    }

    override fun disable() {
        Bukkit.getServer().shutdown()
    }
}