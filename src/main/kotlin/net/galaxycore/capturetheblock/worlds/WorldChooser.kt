package net.galaxycore.capturetheblock.worlds

import java.io.File

fun chooseWorld() {
    val worldFile: File = allWorlds!!.random()
    currentWorld = worldFile.asWorld()!!
}