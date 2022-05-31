@file:Suppress("MemberVisibilityCanBePrivate", "unused")
package net.galaxycore.capturetheblock.game

class GamePhaseSystemBuilder {
    private val baseGamePhases = mutableListOf<BaseGamePhase>()
    fun build() = GamePhaseSystem(*baseGamePhases.toTypedArray())
    fun phase(length: Long, builder: GamePhaseBuilder.() -> Unit) {
        baseGamePhases += GamePhaseBuilder(length).apply(builder).build()
    }
}

class GamePhaseBuilder(val length: Long) {
    private var start: (() -> Unit)? = null
    private var end: (() -> Unit)? = null
    private var cancel: (() -> Unit)? = null
    private var secondFunc: ((string: String) -> Unit)? = null
    private var counterMessage: ((secondsLeft: Long) -> String)? = null
    private var key: String? = null
    private var keyActionBar: String? = null
    private var id: String? = null

    fun start(callback: () -> Unit) {
        start = callback
    }

    fun end(callback: () -> Unit) {
        end = callback
    }

    fun cancel(callback: () -> Unit) {
        cancel = callback
    }

    fun counterMessageTime(callback: (secondsLeft: Long) -> String) {
        counterMessage = callback
    }

    fun counterMessageKey(key: String?) {
        this.key = key
    }

    fun identity(id: String?) {
        this.id = id
    }

    fun counterActionBarMessageKey(key: String?) {
        this.keyActionBar = key
    }

    fun createCounterMessageCallback(
        hours: String = "h",
        minutes: String = "m",
        seconds: String = "s",
    ) {
        counterMessage = buildCounterMessageCallback(hours, minutes, seconds)
    }

    fun build(): BaseGamePhase {
        if (counterMessage == null) {
            createCounterMessageCallback()
        }

        return BaseGamePhase(length, start, end, cancel, key, keyActionBar, counterMessage!!, id, secondFunc)
    }

    fun second(function: (string: String) -> Unit) {
        this.secondFunc = function
    }
}

fun buildGame(builder: GamePhaseSystemBuilder.() -> Unit) = GamePhaseSystemBuilder().apply(builder).build()