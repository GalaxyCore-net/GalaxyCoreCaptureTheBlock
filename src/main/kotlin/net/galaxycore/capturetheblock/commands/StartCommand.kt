package net.galaxycore.capturetheblock.commands

import net.galaxycore.capturetheblock.game.game
import net.galaxycore.capturetheblock.utils.sI18N
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StartCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        game.begin()

        sender.sendMessage("§e✓ Started the game!")

        return true
    }
}