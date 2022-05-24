@file:Suppress("unused")

package net.galaxycore.capturetheblock.utils

import net.galaxycore.capturetheblock.CaptureTheBlock
import net.galaxycore.galaxycorecore.configuration.internationalisation.I18N
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TextReplacementConfig
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin


fun getTraceInfo(): String {
    val trace = Thread.currentThread().stackTrace
    if (trace.size <= 1) {
        return "[${Thread.currentThread().name}/?] "
    }

    val elem = trace[7]
    val clazz = Class.forName(elem.className)
    val formattedPackageName: StringBuilder = StringBuilder()

    for (component: String in clazz.packageName.split(".")) {
        formattedPackageName += "${component.substring(0, 1)}."
    }

    return "[${Thread.currentThread().name}/$formattedPackageName${clazz.simpleName}:${elem.lineNumber}] "
}

operator fun java.lang.StringBuilder.plusAssign(s: String) {
    this.append(s)
}

operator fun java.lang.StringBuilder.plusAssign(s: Long) {
    this.append(s)
}

fun i(string: String) {
    colored {
        CaptureTheBlock.log.info(("✓ " + getTraceInfo()).green + string)
    }
}

private operator fun TextComponent.plus(text: TextComponent): TextComponent {
    return this.append { text }
}

fun d(string: String) {
    colored {
        CaptureTheBlock.log.info(("\uD83D\uDC41 " + getTraceInfo()).cyan + string)
    }
}

fun w(string: String) {
    colored {
        CaptureTheBlock.log.warning(("⨯ " + getTraceInfo()).red + string)
    }
}

val Int.seconds: Int
    get() = this * 20

val Int.minutes: Int
    get() = this.seconds * 60

val Int.hours: Int
    get() = this.minutes * 60

val Long.seconds: Long
    get() = this * 20

val Long.minutes: Long
    get() = this.seconds * 60

val Long.hours: Long
    get() = this.minutes * 60

fun <T> T.applyIfNotNull(block: (T.() -> Unit)?): T {
    if (block != null) apply(block)
    return this
}

fun i18nDE(key: String, value: String, prefix: Boolean) {
    I18N.setDefaultByLang("de_DE", "capturetheblock.$key", value, prefix)
}

fun i18nEN(key: String, value: String, prefix: Boolean) {
    I18N.setDefaultByLang("en_GB", "capturetheblock.$key", value, prefix)
}

fun String.sI18N(player: Player) {
    player.sendMessage(I18N.getC(player, "capturetheblock.$this"))
}

fun String.sI18N(player: Player, replaceable: HashMap<String, String>) {
    var base: String = I18N.getS(player, "capturetheblock.$this")
    replaceable.forEach {
        base = base.replace("%${it.key}%", it.value)
    }

    player.sendMessage(base)
}

@JvmName("sI18NComp")
fun String.sI18N(player: Player, replaceable: HashMap<String, Component>) {
    var component = I18N.getC(player, "capturetheblock.$this")
    for (entry in replaceable) {
        component = component.replaceText(TextReplacementConfig.builder().match("%${entry.key}%").replacement(entry.value).build())
    }
    player.sendMessage(component)
}

fun String.gI18N(player: Player): Component {
    return I18N.getC(player, "capturetheblock.$this")
}

fun String.gI18N(player: Player, replaceable: HashMap<String, String>): String {
    var base: String = I18N.getS(player, "capturetheblock.$this")
    replaceable.forEach {
        base = base.replace("%${it.key}%", it.value)
    }
    return base
}

@JvmName("gI18NComp")
fun String.gI18N(player: Player, replaceable: HashMap<String, Component>): Component {
    var component = I18N.getC(player, "capturetheblock.$this")
    for (entry in replaceable) {
        component = component.replaceText(TextReplacementConfig.builder().match("%${entry.key}%").replacement(entry.value).build())
    }
    return component
}

fun broadcast(i18nKey: String) {
    Bukkit.getOnlinePlayers().forEach {
        i18nKey.sI18N(it)
    }
    Bukkit.getConsoleSender().sendMessage(I18N.getByLang("en_GB", "capturetheblock.$i18nKey"))
}

fun broadcast(i18nKey: String, replaceableProvider: (player: Player) -> HashMap<String, String>) {
    Bukkit.getOnlinePlayers().forEach {
        i18nKey.sI18N(it, replaceableProvider.invoke(it))
    }
    Bukkit.getConsoleSender().sendMessage(I18N.getByLang("en_GB", "capturetheblock.$i18nKey"))
}

@JvmName("broadcastComp")
fun broadcast(i18nKey: String, replaceableProvider: (player: Player) -> HashMap<String, Component>) {
    Bukkit.getOnlinePlayers().forEach {
        i18nKey.sI18N(it, replaceableProvider.invoke(it))
    }
    Bukkit.getConsoleSender().sendMessage(I18N.getByLang("en_GB", "capturetheblock.$i18nKey"))
}

fun broadcast(i18nKey: String, replaceable: HashMap<String, String>) {
    Bukkit.getOnlinePlayers().forEach {
        i18nKey.sI18N(it, replaceable)
    }
    Bukkit.getConsoleSender().sendMessage(I18N.getByLang("en_GB", "capturetheblock.$i18nKey"))
}

@JvmName("broadcastComp")
fun broadcast(i18nKey: String, replaceable: HashMap<String, Component>) {
    Bukkit.getOnlinePlayers().forEach {
        i18nKey.sI18N(it, replaceable)
    }

    Bukkit.getConsoleSender().sendMessage(I18N.getByLang("en_GB", "capturetheblock.$i18nKey"))
}

fun JavaPlugin.forCommandToExecutor(cmd: String, executor: CommandExecutor) {
    this.getCommand(cmd)!!.setExecutor(executor)
}

operator fun <T> Array<T>.minus(other: List<Any>): Any {
    return this.subtract(other.toSet())
}