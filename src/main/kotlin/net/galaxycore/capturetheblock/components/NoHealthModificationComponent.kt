package net.galaxycore.capturetheblock.components

import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent

class NoHealthModificationComponent : NoHungerModificationComponent() {
    @EventHandler fun onDamage(event: EntityDamageEvent) { event.isCancelled = true }
}