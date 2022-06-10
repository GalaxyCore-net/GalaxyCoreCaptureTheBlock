package net.galaxycore.capturetheblock.teams

import net.galaxycore.capturetheblock.cache.OnlinePlayerCache
import kotlin.math.abs
import kotlin.random.Random

fun createTeams() {
    val red = teamRed.votedPlayers
    val blue = teamBlue.votedPlayers

    if (
        (red.size == blue.size) ||
        (abs(red.size - blue.size) == 1)
    ) {
        teamRed.players.addAll(red)
        teamBlue.players.addAll(blue)

        OnlinePlayerCache.instance.onlinePlayers.stream().filter {
            it.team == null
        }.forEach {
            if (teamRed.players.size < teamBlue.players.size) {
                teamRed.players.add(it)
            } else if (teamRed.players.size > teamBlue.players.size){
                teamBlue.players.add(it)
            } else {
                if (Random.nextBoolean()) {
                    teamRed.players.add(it)
                } else {
                    teamBlue.players.add(it)
                }
            }
        }

        return
    }

    var cache = OnlinePlayerCache.instance.onlinePlayers
    cache = cache.shuffled().toMutableList()
    for (player in cache) {
        if (teamRed.players.size < teamBlue.players.size) {
            teamRed.players.add(player)
        } else {
            teamBlue.players.add(player)
        }
    }
}