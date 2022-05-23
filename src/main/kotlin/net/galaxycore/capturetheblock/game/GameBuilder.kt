package net.galaxycore.capturetheblock.game

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.utils.minutes

fun getNewGame(): GamePhaseSystem {
    return buildGame {
        phase(5L.minutes) {
            counterMessageKey("phase.lobby.counter")
            counterActionBarMessageKey("phase.lobby.counter.actionbar")
        }
    }
}

val game: GamePhaseSystem
    get() = PluginInstance.game
