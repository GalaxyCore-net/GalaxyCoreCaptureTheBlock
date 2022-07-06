package net.galaxycore.capturetheblock.components

import net.galaxycore.capturetheblock.teams.Team
import net.galaxycore.capturetheblock.utils.forBothTeams
import net.galaxycore.galaxycorecore.events.ServerTimePassedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

class SpawnerComponent : Listener {
    private val ironResetTime = 16
    private val diamondResetTime = 64
    private var timeToSpawnIron = ironResetTime
    private var timeToSpawnDiamond = diamondResetTime

    @EventHandler
    fun onTimePasses(event: ServerTimePassedEvent) {
        timeToSpawnIron--
        timeToSpawnDiamond--

        if (timeToSpawnIron == 0) {
            forBothTeams {
                spawnIron(it)
            }
            timeToSpawnIron = ironResetTime
        }

        if (timeToSpawnDiamond == 0) {
            forBothTeams {
                spawnDiamond(it)
            }
            timeToSpawnDiamond = diamondResetTime
        }
    }

    private fun spawnDiamond(team: Team) {
        team.ctx.teamSpawnerPos.world.dropItem(team.ctx.teamSpawnerPos, ItemStack(Material.DIAMOND))
    }

    private fun spawnIron(team: Team) {
        team.ctx.teamSpawnerPos.world.dropItem(team.ctx.teamSpawnerPos, ItemStack(Material.IRON_INGOT))
    }
}