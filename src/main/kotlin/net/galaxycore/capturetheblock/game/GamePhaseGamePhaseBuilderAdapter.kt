package net.galaxycore.capturetheblock.game

private var internalGamePhase: GamePhaseEnum = GamePhaseEnum.LOBBY

var GamePhaseSystem.gamePhase: GamePhaseEnum
    get() = internalGamePhase
    set(value) {
        internalGamePhase = value
    }