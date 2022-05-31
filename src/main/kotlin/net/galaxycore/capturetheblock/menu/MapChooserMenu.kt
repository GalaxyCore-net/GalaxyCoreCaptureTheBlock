package net.galaxycore.capturetheblock.menu

import net.galaxycore.capturetheblock.worlds.possibleWorlds
import net.galaxycore.capturetheblock.worlds.playersChoosingWorld
import net.galaxycore.galaxycorecore.spice.KMenu
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class MapChooserMenu(val player: Player) : KMenu() {
    private var world0: KMenuItem
    private var world1: KMenuItem
    private var world2: KMenuItem

    override fun getNameI18NKey() = "capturetheblock.phase.lobby.mapchooser"
    override fun getSize() = 9*3

    init {
        world0 = item(2+9, Material.GRASS_BLOCK, "§e" + possibleWorlds!![0].name).then(vote(0))
        world1 = item(4+9, Material.GRASS_BLOCK, "§e" + possibleWorlds!![1].name).then(vote(1))
        world2 = item(6+9, Material.GRASS_BLOCK, "§e" + possibleWorlds!![2].name).then(vote(2))
    }

    private fun vote(world: Int): (KMenuItem) -> Unit = {
        if (playersChoosingWorld[world].contains(player)) {
            playersChoosingWorld[world].remove(player)
        } else {
            playersChoosingWorld[world].add(player)
        }

        (0 .. 2).toMutableList().let { otherWorlds ->
            otherWorlds.remove(world)
            otherWorlds.forEach {
                playersChoosingWorld[it].remove(player)
            }
        }

        player.playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING.key(), Sound.Source.MASTER, 1f, 1f), Sound.Emitter.self())

        player.closeInventory()
    }

    private fun updateItem(world: Int): (ItemStack) -> ItemStack = { stack ->
        stack.editMeta { meta ->
            meta.lore(
                playersChoosingWorld[world].map { Component.text("§e- ${it.name}") }
            )

            if (playersChoosingWorld[world].contains(player)) {
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

        world0.itemStack.update (updateItem(0))
        world1.itemStack.update (updateItem(1))
        world2.itemStack.update (updateItem(2))
    }

}