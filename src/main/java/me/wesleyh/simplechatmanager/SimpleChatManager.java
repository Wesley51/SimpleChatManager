package me.wesleyh.simplechatmanager;

import me.wesleyh.simplechatmanager.Commands.Clearchat;
import me.wesleyh.simplechatmanager.Commands.Mutechat;
import me.wesleyh.simplechatmanager.Commands.SCM;
import me.wesleyh.simplechatmanager.Commands.Slowchat;
import me.wesleyh.simplechatmanager.Listeners.Chat;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class SimpleChatManager extends JavaPlugin {
    public static HashMap<String, String> messageData = new HashMap<>();
    private static SimpleChatManager instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new Chat(), this);
        getCommand("mutechat").setExecutor(new Mutechat());
        getCommand("clearchat").setExecutor(new Clearchat());
        getCommand("slowchat").setExecutor(new Slowchat());
        PluginCommand cmd = getCommand("scm");
        cmd.setAliases(Collections.singletonList("iodised"));
        cmd.setExecutor(new SCM());
        setupConfig();
        Chat.slowedplayers = new HashMap<>();
        Chat.bannedwords = new ArrayList<>();
        Chat.bannedwords.addAll(getConfig().getStringList("bannedWords"));
        File f = new File(getDataFolder()+File.separator+"messages.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setMessage("muted_global", "&aChat has been &bmuted &aby &b%name%&a.");
        setMessage("unmuted_global", "&aChat has been &bunmuted &aby &b%name%&a.");
        setMessage("muted_silent", "&aYou &bmuted &achat.");
        setMessage("unmuted_silent", "&aYou &bunmuted &achat.");
        setMessage("mutechat_blocked", "&cChat is currently muted!");
        setMessage("bannedwords_blocked", "&aYou cannot say &b%word%&a!");
        setMessage("bannedwords_notify", "&b%name% &atried to say &b%word% &abut it was blocked!");
        setMessage("clear_silent", "&aYou cleared chat! Others cannot see this :)");
        setMessage("clear_global", "&aChat has been cleared by &b%name%&a.");
        setMessage("slow_global", "&aSlowmode has been set to &b%t% &asecond(s) by &b%name%&a.");
        setMessage("slow_silent", "&aYou set the slowmode to &b%t%&a.");
        setMessage("slow_numbers", "&fPlease only enter numbers!");
        setMessage("slow_timeleft", "&aYou must wait &b%t% &asecond(s) before talking again!");
        setMessage("help_noperm", "&cYou do not have permission to see the help menu!");
        setMessage("unknown_args", "&fUnknown args! Proceeding...");

        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        for (String message : config.getConfigurationSection("").getKeys(false)) {
            messageData.put(message, config.getString(message));
        }
        instance = this;
    }
    public static SimpleChatManager getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void setupConfig(){
        if (!getConfig().contains("version")){
            getConfig().addDefault("version", 1);
        }
        if (!getConfig().contains("bannedWords")){
            String[] list = {"fuck", "shit", "hell", "bitch"};
            getConfig().addDefault("bannedWords", list);
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    private void setMessage(String name, String message) {
        File f = new File(getDataFolder()+File.separator+"messages.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        if (!config.isSet(name)) {
            config.set(name, message);
            try {
                config.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
