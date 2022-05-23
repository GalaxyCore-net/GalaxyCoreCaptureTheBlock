package net.galaxycore.capturetheblock.game

import net.galaxycore.capturetheblock.utils.w
import org.bukkit.event.Listener
import kotlin.reflect.KClass

class ListenerPool {
    private val listeners: MutableSet<Listener> = mutableSetOf()

    fun activate(vararg listeners: KClass<Listener>) {
        val toCreate = listeners.filter { listener -> this.listeners.any { it.javaClass == listener.java } }

        toCreate.forEach {
            val listener: Listener = try {
                it.java.getConstructor().newInstance()
            } catch (ignored: NoSuchMethodException) {
                w("Constructor for Listener ${it.simpleName} not found")
                return@forEach
            }

            this.listeners.add(listener)
        }

    }
}