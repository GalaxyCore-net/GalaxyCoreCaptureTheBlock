package net.galaxycore.capturetheblock.components

import net.galaxycore.capturetheblock.game.GameComponent
import net.galaxycore.capturetheblock.teams.Team
import net.galaxycore.capturetheblock.teams.team
import net.galaxycore.capturetheblock.teams.teamBlue
import net.galaxycore.capturetheblock.teams.teamRed
import net.galaxycore.capturetheblock.utils.gI18N
import net.galaxycore.capturetheblock.utils.sI18N
import net.galaxycore.capturetheblock.utils.sendI18N
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ItemStack

class BlockComponent : GameComponent {
    private val blockRed = teamRed.ctx.teamBlockPos.toBlockLocation().hashCode()
    private val blockBlue = teamBlue.ctx.teamBlockPos.toBlockLocation().hashCode()
    private val blocks = mutableListOf(blockRed, blockBlue)

    override fun enableComponent() {
        forBothTeams { it.ctx.teamBlockPos.block.type = it.wool  }
    }

    private fun forBothTeams(function: (Team) -> Unit) {
        function.invoke(teamRed)
        function.invoke(teamBlue)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        // check if block is near a team block
        forBothTeams {
            if (it.ctx.teamBlockPos.toBlockLocation().distance(event.block.location) <= 5) {
                event.isCancelled = true
                "phase.game.cantbuildsavezone".sI18N(event.player)
            }
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val hash = event.block.location.toBlockLocation().hashCode()
        if (!blocks.contains(hash)) {
            return
        }

        val player = event.player

        if ((hash == blockRed && player.team == teamRed) || (hash == blockBlue && player.team == teamBlue)) {
            "phase.game.cantbreakownblock".sI18N(player)
            return
        }

        player.inventory.helmet = ItemStack(event.block.type)
        event.block.type = Material.AIR

        player.sendI18N("phase.game.youbrokeblock")
        player.team?.players!!.forEach {
            if (it == player) return@forEach
            it.sendI18N("phase.game.playerbrokeblock.ownteam", hashMapOf(
                "player" to player.name,
                "color" to "phase.game.playerbrokeblock.${player.team!!.opponent.color}".gI18N(it, hashMapOf<String, String>())
            ))
        }

        player.team?.opponent!!.players.forEach {
            it.sendI18N("phase.game.playerbrokeblock.otherteam", hashMapOf(
                "player" to player.name,
                "color" to "phase.game.playerbrokeblock.${player.team!!.color}".gI18N(it, hashMapOf<String, String>())
            ))
        }
    }

    override fun disableComponent() {
        forBothTeams { it.ctx.teamBlockPos.block.type = Material.AIR }
    }
}

