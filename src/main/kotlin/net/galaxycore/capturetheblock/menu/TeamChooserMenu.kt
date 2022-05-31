package net.galaxycore.capturetheblock.menu

import net.galaxycore.capturetheblock.teams.Team
import net.galaxycore.capturetheblock.teams.teamBlue
import net.galaxycore.capturetheblock.teams.teamRed
import net.galaxycore.galaxycorecore.configuration.internationalisation.I18N
import net.galaxycore.galaxycorecore.spice.KMenu
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class TeamChooserMenu(val player: Player) : KMenu() {
    private var itemTeamRed: KMenuItem
    private var itemTeamBlue: KMenuItem

    override fun getNameI18NKey() = "capturetheblock.phase.lobby.teamchooser"
    override fun getSize() = 9*3

    init {
        itemTeamRed = item(2+9, Material.LEATHER_BOOTS, I18N.getS(player, "capturetheblock.red")).then(vote(teamRed))
        itemTeamBlue = item(6+9, Material.LEATHER_BOOTS, I18N.getS(player, "capturetheblock.blue")).then(vote(teamBlue))
    }

    private fun vote(team: Team): (KMenuItem) -> Unit = {
        if (team.votedPlayers.contains(player)) {
            team.votedPlayers.remove(player)
        } else {
            team.votedPlayers.add(player)
        }

        mutableListOf(teamRed, teamBlue).let { otherTeams ->
            otherTeams.remove(team)
            otherTeams.forEach {
                it.votedPlayers.remove(player)
            }
        }

        player.playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING.key(), Sound.Source.MASTER, 1f, 1f), Sound.Emitter.self())

        player.closeInventory()
    }

    private fun updateItem(team: Team): (ItemStack) -> ItemStack = { stack ->
        stack.editMeta { meta ->
            meta.lore(
                team.votedPlayers.map { Component.text("§e- ${it.name}") }
            )

            if (team.votedPlayers.contains(player)) {
                meta.addEnchant(Enchantment.CHANNELING, 1, true)
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
            } else {
                meta.removeEnchant(Enchantment.CHANNELING)
            }
        }

        stack
    }

    fun open() {
        open(player)

        itemTeamBlue.itemStack.update(updateItem(teamBlue))
        itemTeamRed.itemStack.update(updateItem(teamRed))
    }

}