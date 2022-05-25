package net.galaxycore.capturetheblock.game

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.PluginManagerInst
import net.galaxycore.capturetheblock.utils.*
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class ListenerPool {
    private val listeners: MutableSet<Listener> = mutableSetOf()
    private val activeListeners: MutableSet<Class<out Listener>> = mutableSetOf()

    fun activate(listeners: Array<Class<out Listener>>) {
        val toCreate = listeners.filter { listener -> this.listeners.none { it.javaClass == listener.javaClass } }

        toCreate.forEach {
            val listener: Listener = try {
                val listener: Listener = it.getConstructor().newInstance()
                listener
            } catch (ignored: NoSuchMethodException) {
                w("Constructor for Listener ${it.simpleName} not found")
                return@forEach
            }

            this.listeners.add(listener)
            this.activeListeners.add(it)
            PluginManagerInst.registerEvents(listener, PluginInstance)
            d("Activated new Listener ${it.simpleName}")
        }

        val toActivate = this.listeners
            .filter { listener -> this.activeListeners.none { it == listener.javaClass } }
            .filter { listener -> listeners.any { it.javaClass == listener.javaClass } }

        toActivate.forEach {

            this.activeListeners.add(it.javaClass)
            PluginManagerInst.registerEvents(it, PluginInstance)
            d("Reactivated Listener ${it.javaClass.simpleName}")
        }
    }

    fun deactivate(listeners: Array<Class<out Listener>>) {
        val toRemove = this.listeners.stream()
            .filter { listener -> listeners.any { listener.javaClass == it } }.toList()

        toRemove.forEach {
            HandlerList.unregisterAll(it)
            this.activeListeners.remove(it::class.java)
            d("Deactivated Listener ${it.javaClass.simpleName}")
        }
    }

    fun push(listeners: Array<out Class<out Listener>>) {
        val toActivate = listeners.filter { listener -> this.activeListeners.none { it == listener } }
        val toDeactivate = this.listeners
            .map { it::class.java }
            .filter { listener -> listeners.none { it == listener } }

        activate(toActivate.toTypedArray())
        deactivate(toDeactivate.toTypedArray())
    }
}

