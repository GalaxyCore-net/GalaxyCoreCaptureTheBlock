package net.galaxycore.capturetheblock.phases

import com.okkero.skedule.SynchronizationContext
import com.okkero.skedule.schedule
import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.cache.OnlinePlayerCache
import net.galaxycore.capturetheblock.components.*
import net.galaxycore.capturetheblock.game.Phase
import net.galaxycore.capturetheblock.game.game
import net.galaxycore.capturetheblock.utils.gI18N
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

class LobbyPhase : Phase() {
    override fun onEnable() {
        listenWith(
            TeleportToSpawnComponent::class.java,
            NoBlockModificationComponent::class.java,
            NoHealthModificationComponent::class.java,
            StartGameIfEnoughPlayersComponent::class.java,
        )

        // Configure the game to cancel if there are too few players
        CancelGameIfToLittlePlayersComponent.currentMinPlayers = 2
        CancelGameIfToLittlePlayersComponent.lookOnTeams = false
        CancelGameIfToLittlePlayersComponent.shouldEnd = false

        // Start the pre-game actionbar timer
        startActionBar()
    }

    fun onCountdownStart() {
        PluginInstance.listenerPool.activate(mutableListOf(
                CancelGameIfToLittlePlayersComponent::class.java
            ).toTypedArray())

        PluginInstance.listenerPool.deactivate(mutableListOf(
            StartGameIfEnoughPlayersComponent::class.java
        ).toTypedArray())
    }

    fun onCountdownCancel() {
        PluginInstance.listenerPool.deactivate(mutableListOf(
            CancelGameIfToLittlePlayersComponent::class.java
        ).toTypedArray())

        PluginInstance.listenerPool.activate(mutableListOf(
            StartGameIfEnoughPlayersComponent::class.java
        ).toTypedArray())

        startActionBar()
    }

    private fun startActionBar() {
        Bukkit.getScheduler().schedule(PluginInstance, SynchronizationContext.ASYNC) {
            repeating(20)


            while (!game.isRunning) {
                val size = OnlinePlayerCache.instance.onlinePlayers.size
                val minPlayers = CancelGameIfToLittlePlayersComponent.currentMinPlayers

                Bukkit.getOnlinePlayers().forEach {
                    it.sendActionBar("phase.lobby.actionbar".gI18N(it, hashMapOf(
                        "current" to Component.text(size),
                        "of" to Component.text(minPlayers)
                    )))
                }
                yield()
            }
        }
    }

    override fun onDisable() {

    }
}