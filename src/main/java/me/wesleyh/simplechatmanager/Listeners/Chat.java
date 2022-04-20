package me.wesleyh.simplechatmanager.Listeners;

import me.wesleyh.simplechatmanager.Commands.Mutechat;
import me.wesleyh.simplechatmanager.Commands.Slowchat;
import me.wesleyh.simplechatmanager.SimpleChatManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat implements Listener {
    public static HashMap<Player, Long> slowedplayers;
    public static ArrayList<String> bannedwords;
    @EventHandler
    public void onEvent(AsyncPlayerChatEvent e){
        if (Mutechat.mutechat && !e.getPlayer().hasPermission("scm.mutechat.bypass")){
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("mutechat_blocked")));
            e.setCancelled(true);
            return;
        }
        if (!e.getPlayer().hasPermission("scm.bannedwords.bypass")){
            for (String s : bannedwords){
                if (e.getMessage().contains(s)) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("bannedwords_blocked").replace("%word%", new StringBuilder(s))));
                    for (Player p : Bukkit.getOnlinePlayers()){
                        if (p.hasPermission("scm.bannedwords.notify")){
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("bannedwords_notify").replace("%word%", new StringBuilder(s))).replace("%name%", new StringBuilder(e.getPlayer().getName())));
                        }
                    }
                }

            }
        }

        if (slowedplayers.containsKey(e.getPlayer())) {
            if (slowedplayers.get(e.getPlayer()) < System.currentTimeMillis()) slowedplayers.remove(e.getPlayer());
            else{
                long s = ((slowedplayers.get(e.getPlayer()) - System.currentTimeMillis())/1000);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleChatManager.messageData.get("slow_timeleft").replace("%t%", String.valueOf(s))));
                e.setCancelled(true);
            }
        }
        else if (Slowchat.slowtime > 0) {
            slowedplayers.put(e.getPlayer(), System.currentTimeMillis() + (Slowchat.slowtime * 1000L));
        }

    }
}
