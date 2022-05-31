package net.galaxycore.capturetheblock.components

import net.galaxycore.capturetheblock.menu.MapChooserMenu
import net.galaxycore.capturetheblock.menu.TeamChooserMenu
import net.galaxycore.capturetheblock.teams.teamBlue
import net.galaxycore.capturetheblock.teams.teamRed
import net.galaxycore.capturetheblock.worlds.playersChoosingWorld
import net.galaxycore.galaxycorecore.configuration.internationalisation.I18N
import net.galaxycore.galaxycorecore.events.ServerTimePassedEvent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*
import org.bukkit.inventory.ItemStack

class LobbyHotbarComponent : Listener {
    private val mapChooserItem = hashMapOf<String, ItemStack>()
    private val teamChooserItem = hashMapOf<String, ItemStack>()

    init {
        val i18NImpl = I18N.getInstanceRef().get() as I18N

        i18NImpl.languages.values.forEach {
            mapChooserItem[it.lang] = makeItem(Material.MAP, i18NImpl.get(it.lang, "capturetheblock.phase.lobby.mapchooser"))
            teamChooserItem[it.lang] = makeItem(Material.FEATHER, i18NImpl.get(it.lang, "capturetheblock.phase.lobby.teamchooser"))
        }
    }

    private fun makeItem(material: Material, displayName: String): ItemStack {
        val item = ItemStack(material)
        item.editMeta { it.displayName(Component.text(displayName)) }
        return item
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.player.apply {
            inventory.clear()
            level = 0
            exp = 0.0f
            foodLevel = 20
            health = 20.0
            saturation = 20.0f
        }
    }

    @EventHandler
    fun onTimePassed(event: ServerTimePassedEvent) {
        Bukkit.getOnlinePlayers().forEach {
            it.inventory.apply {
                if (getItem(2) != mapChooserItem[I18N.getInstanceRef().get().getLocale(it)]) {
                    setItem(2, mapChooserItem[I18N.getInstanceRef().get().getLocale(it)])
                }

                if (getItem(6) != teamChooserItem[I18N.getInstanceRef().get().getLocale(it)]) {
                    setItem(6, teamChooserItem[I18N.getInstanceRef().get().getLocale(it)])
                }
            }
        }
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return

        when (event.player.inventory.heldItemSlot) {
            2 -> {
                MapChooserMenu(player = event.player).open()
            }
            6 -> {
                TeamChooserMenu(player = event.player).open()
            }
        }

    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerSwitchHand(event: PlayerSwapHandItemsEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        playersChoosingWorld.forEach { it.remove(event.player) }

        teamRed.votedPlayers.remove(event.player)
        teamBlue.votedPlayers.remove(event.player)
    }

}