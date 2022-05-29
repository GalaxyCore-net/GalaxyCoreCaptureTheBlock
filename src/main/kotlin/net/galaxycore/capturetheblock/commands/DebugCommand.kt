package net.galaxycore.capturetheblock.commands

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.game.game
import net.galaxycore.capturetheblock.utils.plusAssign
import net.galaxycore.capturetheblock.utils.sI18N
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jsoup.Connection
import org.jsoup.Jsoup

import java.io.IOException




class DebugCommand : CommandExecutor, TabCompleter {
    @Throws(IOException::class)
    fun hasteText(message: String): String {
        val response: Connection.Response = Jsoup.connect("https://haste.galaxycore.net/documents")
            .requestBody(message)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .ignoreContentType(true)
            .method(Connection.Method.POST)
            .execute()
        return "https://haste.galaxycore.net/" + response.body().substring(8, 18)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as Player

        if (!player.hasPermission("ctb.debug")) {
            "nopermission".sI18N(player)
            return true
        }

        if (args.isEmpty()) {
            val messagebuilder = StringBuilder()
            messagebuilder += "${PluginInstance.description.fullName} by ${PluginInstance.description.authors.toTypedArray()}\n\n"
            messagebuilder += "Current Thread Dump:\n"
            Thread.getAllStackTraces().forEach{ entry ->
                messagebuilder += "Thread ${entry.key.name}:\n"
                entry.value.forEach{
                    messagebuilder += "    $it\n"
                }
            }

            messagebuilder += "\n\n"
            messagebuilder += "Current Memory:\n"
            messagebuilder += "Used Memory: ${Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()}\n"
            messagebuilder += "Free Memory: ${Runtime.getRuntime().freeMemory()}\n"
            messagebuilder += "Total Memory: ${Runtime.getRuntime().totalMemory()}\n"
            messagebuilder += "Max Memory: ${Runtime.getRuntime().maxMemory()}\n"
            messagebuilder += "Used Memory Percentage: ${(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Runtime.getRuntime().totalMemory() * 100}\n"

            messagebuilder += "\n\n"
            messagebuilder += "Current Server State:\n"
            messagebuilder += "Max Players: ${Bukkit.getServer().maxPlayers}\n"
            messagebuilder += "Online: ${Bukkit.getOnlinePlayers().size}\n"
            messagebuilder += "Online Players: ${Bukkit.getOnlinePlayers().joinToString(", ") { it.name }}\n"
            messagebuilder += "Bukkit Version: ${Bukkit.getBukkitVersion()}\n"
            messagebuilder += "Server Version: ${Bukkit.getServer().javaClass.getPackage().name}\n"
            messagebuilder += "Server Name: ${Bukkit.getServer().name}\n"
            messagebuilder += "Server Port: ${Bukkit.getServer().port}\n"
            messagebuilder += "Server MOTD: ${Bukkit.getServer().motd()}\n"

            messagebuilder += "\n\n"
            messagebuilder += "Current Plugin State:\n"
            messagebuilder += "Plugin Version: ${PluginInstance.description.version}\n"
            messagebuilder += "Plugin Name: ${PluginInstance.description.name}\n"
            messagebuilder += "Plugin Authors: ${PluginInstance.description.authors.toTypedArray()}\n"
            messagebuilder += "Plugin Description: ${PluginInstance.description.description}\n"
            messagebuilder += "Plugin Website: ${PluginInstance.description.website}\n"
            messagebuilder += "Plugin Activation State: ${PluginInstance.isEnabled}\n"

            messagebuilder += "\n\n"
            messagebuilder += "Current Game State:\n"
            messagebuilder += "Game running: ${game.isRunning}\n"
            messagebuilder += "Game Phase LOBBY: loaded=${PluginInstance.lobbyPhase.wasLoaded} activated=${PluginInstance.lobbyPhase.activated} deactivated=${PluginInstance.lobbyPhase.wasDeactivated}\n"
            messagebuilder += "Game Phase PREPARE: loaded=${PluginInstance.prepPhase.wasLoaded} activated=${PluginInstance.prepPhase.activated} deactivated=${PluginInstance.prepPhase.wasDeactivated}\n"
            messagebuilder += "Game Phase GAME: loaded=${PluginInstance.gamePhase.wasLoaded} activated=${PluginInstance.gamePhase.activated} deactivated=${PluginInstance.gamePhase.wasDeactivated}\n"
            messagebuilder += "Game Phase END: loaded=${PluginInstance.endPhase.wasLoaded} activated=${PluginInstance.endPhase.activated} deactivated=${PluginInstance.endPhase.wasDeactivated}\n"

            messagebuilder += "\n\n"
            messagebuilder += "Current Listener State:\n"
            messagebuilder += PluginInstance.listenerPool.dump()

            player.sendMessage(hasteText(messagebuilder.toString()))

        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String> {
        if (!sender.hasPermission("ctb.debug")) {
            return mutableListOf()
        }

        return mutableListOf(
            "skipphase",
            "worlds",
            "settings"
        ).filter {
            it.startsWith(args[0])
        }.toMutableList()
    }
}