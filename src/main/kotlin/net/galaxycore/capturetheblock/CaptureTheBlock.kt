package net.galaxycore.capturetheblock

import net.galaxycore.capturetheblock.commands.StartCommand
import net.galaxycore.capturetheblock.game.GamePhaseSystem
import net.galaxycore.capturetheblock.game.ListenerPool
import net.galaxycore.capturetheblock.game.getNewGame
import net.galaxycore.capturetheblock.utils.*
import net.galaxycore.capturetheblock.utils.KRunnableHolder
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

/**
 * The Main Class of the CaptureTheBlock Plugin
 */
@Suppress("unused")
class CaptureTheBlock : JavaPlugin() {
    private val kRunnableHolderProperty = lazy { KRunnableHolder }
    internal val kRunnableHolder by kRunnableHolderProperty
    internal lateinit var game: GamePhaseSystem
    internal lateinit var listenerPool: ListenerPool

    /**
     * The Plugin Startup Logic. Gets called by the Bukkit API
     */
    override fun onEnable() {
        instance = this
        log = logger

        d("Launching CaptureTheBlock")
        registerI18nDE()
        registerI18nEN()

        d("Introducing Game Phases")
        game = getNewGame()

        d("Searching for Listeners")
        listenerPool = ListenerPool()

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