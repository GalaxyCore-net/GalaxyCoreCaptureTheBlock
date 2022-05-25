package net.galaxycore.capturetheblock.components

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

open class NoHungerModificationComponent : Listener {
    @EventHandler fun onHungerChange(event: FoodLevelChangeEvent) {event.isCancelled = true }
}