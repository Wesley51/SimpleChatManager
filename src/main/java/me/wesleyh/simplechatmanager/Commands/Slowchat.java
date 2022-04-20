package me.wesleyh.simplechatmanager.Commands;

import me.wesleyh.simplechatmanager.Listeners.Chat;
import me.wesleyh.simplechatmanager.SimpleChatManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Slowchat implements CommandExecutor {
    public static int slowtime;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (sender.hasPermission("scm.slowchat")) {
            if (args.length > 0) {
                if (args[0].matches("^[0-9]+$")) {
                    slowtime = Integer.parseInt(args[0]);
                    Chat.slowedplayers.clear();
                }
                else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("slow_numbers")));
                    return true;
                }
                if (args.length == 2 && args[1].equals("-s")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("slow_silent").replace("%t%", String.valueOf(slowtime))));
                    return true;
                }
            }
            else {
                slowtime = 0;
                Chat.slowedplayers.clear();
            }
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("slow_global").replace("%t%", String.valueOf(slowtime)).replace("%name%", sender.getName())));

        }
        return true;
    }
}

