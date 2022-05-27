package net.galaxycore.capturetheblock.worlds

import org.bukkit.entity.Player
import java.io.File

fun chooseWorld() {
    // Get the worlds with the max amount of player votes
    val worlds = getWorldsWithMaxVotes()
    // If there is only one world, choose it
    if (worlds.size == 1) {
        currentWorld = worlds[0].asWorld()!!
        return
    }

    // If there are more than one world, choose a random one
    currentWorld = worlds.random().asWorld()!!
}

fun getWorldsWithMaxVotes(): List<File> {
    val worlds = mutableListOf<File>()
    var maxVotes = 0
    for ((index, world) in possibleWorlds!!.withIndex()) {
        val votes = playersChoosingWorld[index].size
        if (votes > maxVotes) {
            worlds.clear()
            worlds.add(world)
            maxVotes = votes
        } else if (votes == maxVotes) {
            worlds.add(world)
        }
    }
    return worlds
}

val playersChoosingWorld = Array<MutableList<Player>>(3) { mutableListOf() }