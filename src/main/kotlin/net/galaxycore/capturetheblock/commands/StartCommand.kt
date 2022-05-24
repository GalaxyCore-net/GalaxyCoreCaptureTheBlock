package net.galaxycore.capturetheblock.commands

import net.galaxycore.capturetheblock.game.game
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        game.begin()

        sender.sendMessage("§e✓ Started the game!")

        return true
    }
}