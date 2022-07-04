package net.galaxycore.capturetheblock.teams

import net.galaxycore.capturetheblock.worlds.*
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player

class Team(private val id: Int, val wool: Material, val color: String) {
    val opponent: Team get() = if (this == teamRed) teamBlue else teamRed
    val votedPlayers = mutableListOf<Player>()
    val players = mutableListOf<Player>()
    lateinit var ctx: WorldSpecificContext

    fun loadConfig() {
        ctx = WorldSpecificConfig(currentWorld!!.worldFolder.getWorldConfigFile(), true).getCtx(id)
    }

    fun init(player: Player) {
        player.gameMode = GameMode.SURVIVAL
        player.inventory.clear()
        player.teleport(ctx.teamSpawn)
        player.teleport(ctx.teamSpawn)
    }
}


val teamRed = Team(0, Material.RED_WOOL, "red")
val teamBlue = Team(1, Material.BLUE_WOOL, "blue")

val Player.team: Team?
    get() {
        if (teamRed.players.contains(this)) {
            return teamRed
        }
        if (teamBlue.players.contains(this)) {
            return teamBlue
        }
        return null
    }