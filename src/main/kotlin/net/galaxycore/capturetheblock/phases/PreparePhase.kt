package net.galaxycore.capturetheblock.phases

import com.okkero.skedule.SynchronizationContext
import com.okkero.skedule.schedule
import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.components.CancelGameIfToLittlePlayersComponent
import net.galaxycore.capturetheblock.components.NoHealthModificationComponent
import net.galaxycore.capturetheblock.components.PlayerRestrictionComponent
import net.galaxycore.capturetheblock.game.Phase
import net.galaxycore.capturetheblock.teams.team
import net.galaxycore.capturetheblock.teams.teamBlue
import net.galaxycore.capturetheblock.teams.teamRed
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit

class PreparePhase : Phase() {
    override fun enable() {
        listenWith(
            NoHealthModificationComponent::class.java,
            CancelGameIfToLittlePlayersComponent::class.java,
            PlayerRestrictionComponent::class.java
        )

        teamRed.loadConfig()
        teamBlue.loadConfig()

        Bukkit.getScheduler().schedule(PluginInstance, SynchronizationContext.SYNC) {
            waitFor(10)
            for (player in Bukkit.getOnlinePlayers()) {
                player.team?.init(player)
                PlayerRestrictionComponent.PLAYER_RESTRICTIONS.add(player)
            }
        }

        // Configure the game
        CancelGameIfToLittlePlayersComponent.currentMinPlayers = 2
        CancelGameIfToLittlePlayersComponent.lookOnTeams = true
        CancelGameIfToLittlePlayersComponent.shouldEnd = true
    }

    override fun disable() {

    }

    override fun onTick(string: String) {
        Bukkit.getOnlinePlayers().forEach {
            it.showTitle(Title.title(Component.text("§e$string"), Component.text("")))
        }
    }
}
