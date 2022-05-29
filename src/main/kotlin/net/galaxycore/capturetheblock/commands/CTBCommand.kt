package net.galaxycore.capturetheblock.commands

import net.galaxycore.capturetheblock.PluginInstance
import net.galaxycore.galaxycorecore.configuration.PrefixProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CTBCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val desc = PluginInstance.description
        sender.sendMessage("${PrefixProvider.getPrefix()} ${desc.name}@${desc.version} by ${desc.authors.joinToString(", ")}")
        return true
    }
}