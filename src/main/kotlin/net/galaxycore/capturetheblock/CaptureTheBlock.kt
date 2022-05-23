package net.galaxycore.capturetheblock

import net.galaxycore.capturetheblock.utils.i
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

/**
 * The Main Class of the CaptureTheBlock Plugin
 */
@Suppress("unused")
class CaptureTheBlock : JavaPlugin() {

    /**
     * The Plugin Startup Logic. Gets called by the Bukkit API
     */
    override fun onEnable() {
        log = logger

        i("CaptureTheBlock was loaded")
    }

    companion object {
        lateinit var log: Logger
    }
}
