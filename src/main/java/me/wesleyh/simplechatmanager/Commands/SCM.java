package me.wesleyh.simplechatmanager.Commands;

import me.wesleyh.simplechatmanager.Listeners.Chat;
import me.wesleyh.simplechatmanager.SimpleChatManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SCM implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (args.length > 0){
            if (args[0].equals("reload") && sender.hasPermission("scm.reload")){
                reloadConfigs();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfig.yml and Messages.yml have been reloaded!"));
                return true;
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lSimpleChatManager &r&a[&bv1.0&a] &r&7by WesleyH21"));
        if (sender.hasPermission("scm.admin")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n&a/clearchat [-s] &6- &rClear the chat"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/mutechat [-s] &6- &rSlow the chat"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/slowchat [time] [-s] &6- &rSlow the chat"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n&b&l► &r&bNeed help? &aContact &nWes#8715"));
        }
        else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n" + SimpleChatManager.messageData.get("help_noperm")));
        }
        return true;
    }
    public void reloadConfigs(){
        File f = new File(SimpleChatManager.getInstance().getDataFolder()+File.separator+"messages.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        SimpleChatManager.messageData.clear();
        for (String message : config.getConfigurationSection("").getKeys(false)) {
            SimpleChatManager.messageData.put(message, config.getString(message));
        }
        SimpleChatManager.getInstance().reloadConfig();
        Chat.bannedwords.addAll(SimpleChatManager.getInstance().getConfig().getStringList("bannedWords"));

    }


}
