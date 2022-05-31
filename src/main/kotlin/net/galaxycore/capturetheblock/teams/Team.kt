package net.galaxycore.capturetheblock.teams

import net.galaxycore.capturetheblock.worlds.WorldSpecificConfig
import net.galaxycore.capturetheblock.worlds.WorldSpecificContext
import net.galaxycore.capturetheblock.worlds.currentWorld
import net.galaxycore.capturetheblock.worlds.getWorldConfigFile
import org.bukkit.entity.Player

class Team(private val id: Int) {
    val votedPlayers = mutableListOf<Player>()
    val players = mutableListOf<Player>()
    lateinit var ctx: WorldSpecificContext

    fun loadConfig() {
        ctx = WorldSpecificConfig(currentWorld!!.worldFolder.getWorldConfigFile()).getCtx(id)
    }
}


val teamRed = Team(0)
val teamBlue = Team(1)