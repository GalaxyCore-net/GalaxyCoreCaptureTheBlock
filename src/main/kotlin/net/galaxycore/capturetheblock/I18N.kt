package net.galaxycore.capturetheblock

import net.galaxycore.capturetheblock.utils.i18nDE
import net.galaxycore.capturetheblock.utils.i18nEN

fun registerI18nDE(){
    fun i18n(key: String, value: String, prefix: Boolean) { i18nDE(key, value, prefix) }

    i18n("phase.lobby.counter", "§eDas Spiel startet in %time%", true)
    i18n("phase.lobby.counter.actionbar", "§e%time%", false)
}

fun registerI18nEN() {
    fun i18n(key: String, value: String, prefix: Boolean) { i18nEN(key, value, prefix) }

    i18n("phase.lobby.counter", "§eStarting in %time%", true)
    i18n("phase.lobby.counter.actionbar", "§e%time%", false)
}