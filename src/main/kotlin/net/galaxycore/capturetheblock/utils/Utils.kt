@file:Suppress("unused")

package net.galaxycore.capturetheblock.utils

import net.galaxycore.capturetheblock.CaptureTheBlock


fun getTraceInfo(): String {
    val trace = Thread.currentThread().stackTrace
    if (trace.size <= 1) {
        return "[${Thread.currentThread().name}/?] "
    }

    val elem = trace[3]
    val clazz = Class.forName(elem.className)
    val formattedPackageName: StringBuilder = StringBuilder()

    for (component: String in clazz.packageName.split("."))  {
        formattedPackageName += "${component.substring(0, 2)}."
    }

    return "[${Thread.currentThread().name}/$formattedPackageName${clazz.simpleName}:${elem.lineNumber}] "
}

operator fun java.lang.StringBuilder.plusAssign(s: String) {
    this.append(s)
}

fun i(string: String) {
    CaptureTheBlock.log.info(getTraceInfo() + string)
}

fun d(string: String) {
    CaptureTheBlock.log.fine(getTraceInfo() + string)
}

fun w(string: String) {
    CaptureTheBlock.log.warning(getTraceInfo() + string)
}

val Int.seconds: Int
    get() = this * 20

val Int.minutes: Int
    get() = this.seconds * 60

val Int.hours: Int
    get() = this.minutes * 60