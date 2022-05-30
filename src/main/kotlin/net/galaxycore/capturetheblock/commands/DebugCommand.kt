package net.galaxycore.capturetheblock.commands

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.capturetheblock.game.game
import net.galaxycore.capturetheblock.utils.plusAssign
import net.galaxycore.capturetheblock.utils.sI18N
import net.galaxycore.capturetheblock.worlds.*
import net.galaxycore.galaxycorecore.configuration.PrefixProvider
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
    private val zen = arrayOf(
        "Beautiful is better than ugly.",
        "Explicit is better than implicit.",
        "Simple is better than complex.",
        "Complex is better than complicated.",
        "Flat is better than nested.",
        "Sparse is better than dense.",
        "Readability counts.",
        "Special cases aren't special enough to break the rules.",
        "Although practicality beats purity.",
        "Errors should never pass silently.",
        "Unless explicitly silenced.",
        "In the face of ambiguity, refuse the temptation to guess.",
        "There should be one-- and preferably only one --obvious way to do it.",
        "Although that way may not be obvious at first unless you're Dutch.",
        "Now is better than never.",
        "Although never is often better than *right* now.",
        "If the implementation is hard to explain, it's a bad idea.",
        "If the implementation is easy to explain, it may be a good idea.",
        "Namespaces are one honking great idea -- let's do more of those!"
    )

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

    private fun getSettingsDump(): String {
        val bob = StringBuilder()

        bob += "Settings dump:\n"

        if(currentWorld == null) {
            bob += "No world selected"
            return bob.toString()
        }

        val config = WorldSpecificConfig(currentWorld!!.worldFolder.getWorldConfigFile())

        bob += "World: " + currentWorld!!.name + "\n"
        bob += "WorldConfig/isEnabled = ${config.isEnabled}\n"
        bob += "WorldConfig/environment = ${config.environment}\n"
        bob += "WorldConfig/generator = ${config.generator}\n"
        bob += "WorldConfig/teamRedSpawn = ${config.teamRedSpawn}\n"
        bob += "WorldConfig/teamRedAreaMin = ${config.teamRedAreaMin}\n"
        bob += "WorldConfig/teamRedAreaMax = ${config.teamRedAreaMax}\n"
        bob += "WorldConfig/teamRedBlockPos = ${config.teamRedBlockPos}\n"
        bob += "WorldConfig/teamRedShopPos = ${config.teamRedShopPos}\n"
        bob += "WorldConfig/teamRedSpawnerPos = ${config.teamRedSpawnerPos}\n"
        bob += "WorldConfig/teamBlueSpawn = ${config.teamBlueSpawn}\n"
        bob += "WorldConfig/teamBlueAreaMin = ${config.teamBlueAreaMin}\n"
        bob += "WorldConfig/teamBlueAreaMax = ${config.teamBlueAreaMax}\n"
        bob += "WorldConfig/teamBlueBlockPos = ${config.teamBlueBlockPos}\n"
        bob += "WorldConfig/teamBlueShopPos = ${config.teamBlueShopPos}\n"
        bob += "WorldConfig/teamBlueSpawnerPos = ${config.teamBlueSpawnerPos}\n"

        return bob.toString()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as Player

        if (!player.hasPermission("ctb.debug")) {
            "nopermission".sI18N(player)
            return true
        }

        if (args.isEmpty()) {
            val bob = StringBuilder()

            bob += "// ${zen.random()}\n"

            bob += "${PluginInstance.description.fullName} by ${PluginInstance.description.authors.toTypedArray()}\n\n"
            bob += "Current Thread Dump:\n"
            Thread.getAllStackTraces().forEach { entry ->
                bob += "Thread ${entry.key.name}:\n"
                entry.value.forEach {
                    bob += "    $it\n"
                }
            }

            bob += "\n\n"
            bob += "Current Memory:\n"
            bob += "Used Memory: ${Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()}\n"
            bob += "Free Memory: ${Runtime.getRuntime().freeMemory()}\n"
            bob += "Total Memory: ${Runtime.getRuntime().totalMemory()}\n"
            bob += "Max Memory: ${Runtime.getRuntime().maxMemory()}\n"
            bob += "Used Memory Percentage: ${(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Runtime.getRuntime().totalMemory() * 100}\n"

            bob += "\n\n"
            bob += "Current Server State:\n"
            bob += "Max Players: ${Bukkit.getServer().maxPlayers}\n"
            bob += "Online: ${Bukkit.getOnlinePlayers().size}\n"
            bob += "Online Players: ${Bukkit.getOnlinePlayers().joinToString(", ") { it.name }}\n"
            bob += "Bukkit Version: ${Bukkit.getBukkitVersion()}\n"
            bob += "Server Version: ${Bukkit.getServer().javaClass.getPackage().name}\n"
            bob += "Server Name: ${Bukkit.getServer().name}\n"
            bob += "Server Port: ${Bukkit.getServer().port}\n"
            bob += "Server MOTD: ${Bukkit.getServer().motd()}\n"

            bob += "\n\n"
            bob += "Current Plugin State:\n"
            bob += "Plugin Version: ${PluginInstance.description.version}\n"
            bob += "Plugin Name: ${PluginInstance.description.name}\n"
            bob += "Plugin Authors: ${PluginInstance.description.authors.toTypedArray()}\n"
            bob += "Plugin Description: ${PluginInstance.description.description}\n"
            bob += "Plugin Website: ${PluginInstance.description.website}\n"
            bob += "Plugin Activation State: ${PluginInstance.isEnabled}\n"

            bob += "\n\n"
            bob += "Current Game State:\n"
            bob += "Game running: ${game.isRunning}\n"
            bob += "Game Phase LOBBY: loaded=${PluginInstance.lobbyPhase.wasLoaded} activated=${PluginInstance.lobbyPhase.activated} deactivated=${PluginInstance.lobbyPhase.wasDeactivated}\n"
            bob += "Game Phase PREPARE: loaded=${PluginInstance.prepPhase.wasLoaded} activated=${PluginInstance.prepPhase.activated} deactivated=${PluginInstance.prepPhase.wasDeactivated}\n"
            bob += "Game Phase GAME: loaded=${PluginInstance.gamePhase.wasLoaded} activated=${PluginInstance.gamePhase.activated} deactivated=${PluginInstance.gamePhase.wasDeactivated}\n"
            bob += "Game Phase END: loaded=${PluginInstance.endPhase.wasLoaded} activated=${PluginInstance.endPhase.activated} deactivated=${PluginInstance.endPhase.wasDeactivated}\n"

            bob += "\n\n"
            bob += "Current Listener State:\n"
            bob += PluginInstance.listenerPool.dump()

            bob += "\n\n"
            bob += "Current World State:\n"
            bob += "Loaded Worlds: ${Bukkit.getWorlds().joinToString(", ") { it.name }}\n"
            bob += "Loaded Chunks: ${Bukkit.getWorlds().sumOf { it.loadedChunks.size }}\n"
            bob += "Loaded Entities: ${Bukkit.getWorlds().sumOf { it.entities.size }}\n"
            bob += "Loaded TileEntities: ${Bukkit.getWorlds().sumOf { it.tileEntityCount }}\n"
            bob += "Current World: ${currentWorld?.name}\n"
            bob += "All CTB Worlds: ${allWorlds?.joinToString(", ") { it.name }}\n"
            bob += "Possible Worlds: ${possibleWorlds?.joinToString(", ") { it.name }}\n"

            bob += "\n\n"
            bob += getSettingsDump()

            player.sendMessage(PrefixProvider.getPrefix() + "§eCore Dump: " + hasteText(bob.toString()))
            return true
        }

        if (args.size == 1) {
            when(args[0].lowercase()) {
                "skipphase" -> {
                    game.skip()
                    player.sendMessage(PrefixProvider.getPrefix() + "§eSkipped Phase")
                    return true
                }
                "settings" -> {
                    player.sendMessage(PrefixProvider.getPrefix() + "§eSettings Dump: " + hasteText(getSettingsDump()))
                    return true
                }
            }
        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String> {
        if (!sender.hasPermission("ctb.debug")) {
            return mutableListOf()
        }

        return mutableListOf(
            "skipphase",
            "settings"
        ).filter {
            it.startsWith(args[0])
        }.toMutableList()
    }
}