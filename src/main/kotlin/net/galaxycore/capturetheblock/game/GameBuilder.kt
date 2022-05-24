package net.galaxycore.capturetheblock.game

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.utils.hours
import net.galaxycore.capturetheblock.utils.minutes
import net.galaxycore.capturetheblock.utils.seconds

fun getNewGame(): GamePhaseSystem {
    return buildGame {
        phase(30L.seconds) {
            /* Lobby Countdown */
            counterMessageKey("phase.lobby.counter")
            counterActionBarMessageKey("phase.lobby.counter.actionbar")

            end {
                PluginInstance.lobbyPhase.onDisable()
            }
        }

        phase(30L.seconds) {
            /* Game Preparation Time */
            counterMessageKey("phase.prep.counter")
            counterActionBarMessageKey("phase.prep.counter.actionbar")

            start {
                PluginInstance.prepPhase.onEnable()
            }

            end {
                PluginInstance.prepPhase.onDisable()
            }
        }

        phase(1L.hours - 30L.seconds) {
            /* Game */
            counterMessageKey("phase.game.counter")
            counterActionBarMessageKey("phase.game.counter.actionbar")

            start {
                PluginInstance.gamePhase.onEnable()
            }

            end {
                PluginInstance.gamePhase.onDisable()
            }
        }

        phase(30L.seconds) {
            /* Game End */
            counterMessageKey("phase.end.counter")
            counterActionBarMessageKey("phase.end.counter.actionbar")

            start {
                PluginInstance.endPhase.onEnable()
            }

            end {
                PluginInstance.endPhase.onDisable()
            }
        }
    }
}

val game: GamePhaseSystem
    get() = PluginInstance.game
