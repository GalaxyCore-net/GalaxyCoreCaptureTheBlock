package net.galaxycore.capturetheblock

import net.galaxycore.capturetheblock.utils.i18nDE
import net.galaxycore.capturetheblock.utils.i18nEN

fun registerI18nDE(){
    fun i18n(key: String, value: String, prefix: Boolean) { i18nDE(key, value, prefix) }

    i18n("nopermission", "§c✗§7 Du hast hierfür keine Rechte", true)
    i18n("red", "§cRot", false)
    i18n("blue", "§9Blau", false)

    i18n("phase.lobby.actionbar", "§e(%current%/%of%)", false)
    i18n("phase.lobby.counter", "§eDas Spiel startet in %time%", true)
    i18n("phase.lobby.counter.actionbar", "§e%time%", false)
    i18n("phase.lobby.mapchooser", "§eWähle eine Map", false)
    i18n("phase.lobby.teamchooser", "§eWähle eine Team", false)

    i18n("phase.prep.counter", "§eEs geht in %time% los", true)
    i18n("phase.prep.counter.actionbar", "§eBereite dich vor! | %time%", false)
    i18n("phase.prep.restriction", "§cDu kannst dich noch nicht außerhalb deines Teambereichs begeben!", true)

    i18n("phase.game.counter", "§eDas Spiel endet in %time%", true)
    i18n("phase.game.counter.actionbar", "§e%time%", false)

    i18n("phase.end.counter", "§eDer Server schließt in %time%", true)
    i18n("phase.end.counter.actionbar", "§eDer Server schließt in %time%", false)

    i18n("command.start.success", "§e✓§7 Das Spiel wurde gestartet!", true)
    i18n("command.start.failure", "§c✗§7 Das Spiel ist bereits gestartet!", true)
}

fun registerI18nEN() {
    fun i18n(key: String, value: String, prefix: Boolean) { i18nEN(key, value, prefix) }

    i18n("nopermission", "§c✗§7 You don't have permission to do this", true)
    i18n("red", "§cRed", false)
    i18n("blue", "§9Blue", false)

    i18n("phase.lobby.actionbar", "§e(%current%/%of%)", false)
    i18n("phase.lobby.counter", "§eStarting in %time%", true)
    i18n("phase.lobby.counter.actionbar", "§e%time%", false)
    i18n("phase.lobby.mapchooser", "§eChoose a map", false)
    i18n("phase.lobby.teamchooser", "§eChoose a team", false)

    i18n("phase.prep.counter", "§eThe protection time ends in %time%", true)
    i18n("phase.prep.counter.actionbar", "§eGame starts in %time%", false)
    i18n("phase.prep.restriction", "§cYou currently can't move outside your team area!", true)

    i18n("phase.game.counter", "§eThe game ends in %time%", true)
    i18n("phase.game.counter.actionbar", "§e%time%", false)

    i18n("phase.end.counter", "§eThe server closes in %time%", true)
    i18n("phase.end.counter.actionbar", "§eThe server closes in %time%", false)

    i18n("command.start.success", "§e✓§7 Started the game!", true)
    i18n("command.start.failure", "§c✗§7 The game is already started!", true)

}