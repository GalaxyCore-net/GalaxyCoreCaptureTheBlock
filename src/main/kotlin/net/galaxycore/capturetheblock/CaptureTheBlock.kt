package net.galaxycore.capturetheblock

import net.galaxycore.capturetheblock.commands.StartCommand
import net.galaxycore.capturetheblock.game.GamePhaseSystem
import net.galaxycore.capturetheblock.game.ListenerPool
import net.galaxycore.capturetheblock.game.Phase
import net.galaxycore.capturetheblock.game.getNewGame
import net.galaxycore.capturetheblock.phases.EndPhase
import net.galaxycore.capturetheblock.phases.GamePhase
import net.galaxycore.capturetheblock.phases.LobbyPhase
import net.galaxycore.capturetheblock.phases.PreparePhase
import net.galaxycore.capturetheblock.utils.*
import net.galaxycore.capturetheblock.utils.KRunnableHolder
import net.galaxycore.galaxycorecore.apiutils.CoreProvider
import net.galaxycore.galaxycorecore.configuration.ConfigNamespace
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

/**
 * The Main Class of the CaptureTheBlock Plugin
 */
@Suppress("unused")
class CaptureTheBlock : JavaPlugin() {
    private val kRunnableHolderProperty = lazy { KRunnableHolder }
    internal lateinit var lobbyPhase: Phase
    internal  lateinit var prepPhase: Phase
    internal  lateinit var gamePhase: Phase
    internal  lateinit var endPhase: Phase
    internal lateinit var game: GamePhaseSystem
    internal lateinit var listenerPool: ListenerPool
    internal val kRunnableHolder by kRunnableHolderProperty
    internal lateinit var config: ConfigNamespace

    /**
     * The Plugin Startup Logic. Gets called by the Bukkit API
     */
    override fun onEnable() {
        instance = this
        log = logger

        d("Launching CaptureTheBlock")

        config = CoreProvider.getCore().databaseConfiguration.getNamespace("capturetheblock")
        config.setDefault("lobby.spawn", "world@-25.0;66.1;63.0;180.0;0.0")

        registerI18nDE()
        registerI18nEN()

        d("Introducing Game Phases")
        game = getNewGame()

        d("Initializing Phases")
        listenerPool = ListenerPool()
        lobbyPhase = LobbyPhase()
        prepPhase = PreparePhase()
        gamePhase = GamePhase()
        endPhase = EndPhase()

        firstSync {
            d("Starting Game")
            lobbyPhase.onEnable()
        }.execute()

        d("Loading Command Map")
        forCommandToExecutor("start", StartCommand())

        i("CaptureTheBlock was loaded")
    }

    companion object {
        lateinit var instance: CaptureTheBlock
        lateinit var log: Logger
    }
}

val PluginInstance: CaptureTheBlock
    get() = CaptureTheBlock.instance

val PluginManagerInst: PluginManager
    get() = Bukkit.getPluginManager()