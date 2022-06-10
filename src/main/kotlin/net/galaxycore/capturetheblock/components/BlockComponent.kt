package net.galaxycore.capturetheblock.components

import net.galaxycore.capturetheblock.game.GameComponent
import net.galaxycore.capturetheblock.teams.Team
import net.galaxycore.capturetheblock.teams.teamBlue
import net.galaxycore.capturetheblock.teams.teamRed
import org.bukkit.Material

class BlockComponent : GameComponent {
    override fun enableComponent() {
        forBothTeams { it.ctx.teamBlockPos.block.type = it.wool  }
    }

    private fun forBothTeams(function: (Team) -> Unit) {
        function.invoke(teamRed)
        function.invoke(teamBlue)
    }


    override fun disableComponent() {
        forBothTeams { it.ctx.teamBlockPos.block.type = Material.AIR }
    }
}