package net.galaxycore.capturetheblock

import net.galaxycore.capturetheblock.utils.i18nDE
import net.galaxycore.capturetheblock.utils.i18nEN

fun registerI18nDE(){
    fun i18n(key: String, value: String, prefix: Boolean) { i18nDE(key, value, prefix) }

    i18n("phase.lobby.counter", "§eDas Spiel startet in %time%", true)
    i18n("phase.lobby.counter.actionbar", "§e%time%", false)

    i18n("phase.prep.counter", "§eDie Schutzzeit endet in %time%", true)
    i18n("phase.prep.counter.actionbar", "§eBereite dich vor! | %time%", false)

    i18n("phase.game.counter", "§eDas Spiel endet in %time%", true)
    i18n("phase.game.counter.actionbar", "§e%time%", false)

    i18n("phase.end.counter", "§eDer Server schließt in %time%", true)
    i18n("phase.end.counter.actionbar", "§eDer Server schließt in %time%", false)
}

fun registerI18nEN() {
    fun i18n(key: String, value: String, prefix: Boolean) { i18nEN(key, value, prefix) }

    i18n("phase.lobby.counter", "§eStarting in %time%", true)
    i18n("phase.lobby.counter.actionbar", "§e%time%", false)

    i18n("phase.prep.counter", "§eThe protection time ends in %time%", true)
    i18n("phase.prep.counter.actionbar", "§ePrepare yourself! | %time%", false)

    i18n("phase.game.counter", "§eThe game ends in %time%", true)
    i18n("phase.game.counter.actionbar", "§e%time%", false)

    i18n("phase.end.counter", "§eThe server closes in %time%", true)
    i18n("phase.end.counter.actionbar", "§eThe server closes in %time%", false)
}