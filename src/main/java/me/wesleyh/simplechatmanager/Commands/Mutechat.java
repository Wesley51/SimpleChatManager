package me.wesleyh.simplechatmanager.Commands;

import me.wesleyh.simplechatmanager.SimpleChatManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Mutechat implements CommandExecutor {
    public static boolean mutechat = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (sender.hasPermission("scm.mutechat")) {
            if (args.length > 0) {
                if (args[0].equals("-s")) {
                    mutechat = !mutechat;
                    if (mutechat) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("unmuted_silent")));
                    else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("muted_silent")));
                    return true;
                }
                else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("unknown_args")));
                }
            }
            mutechat = !mutechat;
            if (!mutechat) Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("unmuted_global").replace("%name%", new StringBuilder(sender.getName()))));
            else Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("muted_global").replace("%name%", new StringBuilder(sender.getName()))));
        }
        return true;
    }

}
