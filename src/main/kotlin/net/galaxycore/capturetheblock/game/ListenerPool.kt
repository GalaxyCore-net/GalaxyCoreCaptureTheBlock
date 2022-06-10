package net.galaxycore.capturetheblock.game

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.PluginManagerInst
import net.galaxycore.capturetheblock.utils.*
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class ListenerPool {
    private val listeners: MutableSet<Listener> = mutableSetOf()
    private val activeListeners: MutableSet<Class<out Listener>> = mutableSetOf()
    private val activeComponents: MutableSet<Class<out GameComponent>> = mutableSetOf()

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

            if (listener is GameComponent) {
                listener.enableComponent()
                this.activeComponents.add(listener.javaClass)
            }

            PluginManagerInst.registerEvents(listener, PluginInstance)
        }

        val toActivate = this.listeners
            .filter { listener -> this.activeListeners.none { it == listener.javaClass } }
            .filter { listener -> listeners.any { it.javaClass == listener.javaClass } }

        toActivate.forEach {
            if (it is GameComponent) {
                it.enableComponent()
                this.activeComponents.add(it.javaClass)
            }

            this.activeListeners.add(it.javaClass)
            PluginManagerInst.registerEvents(it, PluginInstance)
        }
    }

    fun deactivate(listeners: Array<Class<out Listener>>) {
        val toRemove = this.listeners.stream()
            .filter { listener -> listeners.any { listener.javaClass == it } }.toList()

        toRemove.forEach { listener ->
            HandlerList.unregisterAll(listener)
            this.activeListeners.remove(listener::class.java)

            if (listener is GameComponent) {
                listener.disableComponent()
                this.activeComponents.remove(listener::class.java)
            }
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

    fun dump(): String {
        return """
            |Active Listeners:
            |${this.activeListeners.joinToString("\n") { it.simpleName }}
            |All Listeners:
            |${this.listeners.joinToString("\n") { it.javaClass.simpleName }}
        """.trimMargin("|")
    }
}

