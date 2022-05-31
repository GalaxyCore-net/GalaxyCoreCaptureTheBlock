package net.galaxycore.capturetheblock.teams

import kotlin.math.abs

fun createTeams() {
    val red = teamRed.votedPlayers
    val blue = teamBlue.votedPlayers

    if (
        (red.size == blue.size) ||
        (abs(red.size - blue.size) == 1)
    ) {
        teamRed.players.addAll(red)
        teamBlue.players.addAll(blue)
        return
    }

    var cache = red + blue
    cache = cache.shuffled()
    for (player in cache) {
        if (teamRed.players.size < teamBlue.players.size) {
            teamRed.players.add(player)
        } else {
            teamBlue.players.add(player)
        }
    }
}