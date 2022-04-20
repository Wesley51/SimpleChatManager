package me.wesleyh.simplechatmanager.Commands;

import me.wesleyh.simplechatmanager.SimpleChatManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Clearchat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (sender.hasPermission("scm.clearchat")){
            // Yes this is weird. But this is the only way to bypass LunarClient anti-spam :)
            String s = "";
            for (int i = 0; i < 1000; i++) {
                if (s.length() > 200) s = " "; s = s + " ";
                for (Player p: Bukkit.getOnlinePlayers()) if (!p.hasPermission("scm.clearchat.bypass")) p.sendMessage(s);
            }
            if (args.length > 0) {
                if (args[0].equals("-s")){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("clear_silent")));
                    return true;
                }
                else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("unknown_args")));
                }
            }else{
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("clear_global").replace("%name%", new StringBuilder(sender.getName()))));
            }

        }
        return true;
    }

}
