@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package net.galaxycore.capturetheblock.game

import net.galaxycore.capturetheblock.utils.*
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import kotlin.math.floor

class GamePhaseSystem(vararg val baseGamePhases: BaseGamePhase) {
    var gamePhases = baseGamePhases.toMutableList()
    var isRunning = false
    var currentPhase: BaseGamePhase? = null
    fun begin() {
        isRunning = true
        currentPhase = gamePhases.removeAt(0)
        currentPhase!!.startIt(gamePhases)
    }

    fun cancel() {
        currentPhase?.stopIt()
        isRunning = false
        currentPhase = null
        gamePhases = baseGamePhases.toMutableList()
    }
}

fun buildCounterMessageCallback(
    hour: String = "h",
    minutes: String = "m",
    seconds: String = "s",
): (Long) -> String = { curSeconds ->
    StringBuilder().apply {
        val hourTime = (curSeconds / 3600)
        val minuteTime = ((curSeconds % 3600) / 60)
        val secondsTime = (curSeconds % 60)

        if (hourTime > 0) {
            this += hourTime
            this += hour
            this += " "
        }

        if (minuteTime > 0) {
            this += minuteTime
            this += minutes
            this += " "
        }

        if (secondsTime > 0) {
            this += secondsTime
            this += seconds
            this += " "
        }

    }.toString()
}

class BaseGamePhase(
    val length: Long,
    val start: (() -> Unit)?,
    val end: (() -> Unit)?,
    val counterMessageKey: String?,
    val counterMessageActionBarKey: String?,
    val counterMessage: ((secondsLeft: Long) -> String),
) {
    private var runnable: KSpigotRunnable? = null

    fun startIt(phaseQueue: MutableList<BaseGamePhase>) {
        start?.invoke()
        runnable = task(period = 20, howOften = (length / 20) + 1, endCallback = {
            end?.invoke()

            if (phaseQueue.isNotEmpty()) phaseQueue.removeAt(0).startIt(phaseQueue)
        }) {
            val currentCounter = it.counterDownToZero
            if (currentCounter?.isCounterValue == true) {
                if (counterMessageKey != null) {
                    if (counterMessageKey != "") {
                        broadcast(
                            counterMessageKey,
                            hashMapOf(
                                "time" to counterMessage.invoke(currentCounter)
                            ),
                        )
                    }
                }
            }

            if (counterMessageActionBarKey != null && currentCounter != null) {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendActionBar(
                        Component.text(
                            counterMessageActionBarKey.gI18N(
                                player,
                                hashMapOf(
                                    "time" to counterMessage.invoke(currentCounter)
                                ),
                            )
                        )
                    )
                }
            }
        }
    }

    fun stopIt() {
        d("Stopping phase...")
        d("$runnable")
        runnable?.forceStop()
    }
}

private val Long.isCounterValue: Boolean
    get() {
        if (this <= 60) {
            return when (this) {
                1L, 2L, 3L, 4L, 5L, 10L, 15L, 20L, 30L -> true
                0L -> false
                else -> this % 60 == 0L
            }
        }

        val minTime: Float = (this / 60F)
        if (minTime != floor(minTime)) return false

        return when (val minutes = (this % 3600) / 60) {
            1L, 2L, 3L, 4L, 5L, 10L, 15L, 20L, 30L -> true
            0L -> false
            else -> minutes % 60 == 0L
        }
    }