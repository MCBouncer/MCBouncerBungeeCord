package com.mcbouncer.bungee;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.YamlConfig;
import com.mcbouncer.api.MCBouncerImplementation;
import com.mcbouncer.api.MCBouncerPlayer;
import com.mcbouncer.bungee.commands.AddnoteCommand;
import com.mcbouncer.bungee.commands.AddgnoteCommand;
import com.mcbouncer.bungee.commands.BanCommand;
import com.mcbouncer.bungee.commands.KickCommand;
import com.mcbouncer.bungee.commands.LookupCommand;
import com.mcbouncer.bungee.commands.RemovenoteCommand;
import com.mcbouncer.bungee.commands.UnbanCommand;
import com.mcbouncer.bungee.listener.ProxiedPlayerListener;
import com.mcbouncer.exceptions.MCBouncerException;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Level;

public class MCBouncerBungeeCord extends Plugin implements MCBouncerImplementation {

    public MCBouncer mcbouncer;
    public MainConfig config;
    
    @Override
    public void onEnable() {
        this.mcbouncer = new MCBouncer(this, new YamlConfig(this));
        getProxy().getPluginManager().registerListener(this, new ProxiedPlayerListener(this));
        getProxy().getPluginManager().registerCommand(this, new AddgnoteCommand(this));
        getProxy().getPluginManager().registerCommand(this, new AddnoteCommand(this));
        getProxy().getPluginManager().registerCommand(this, new BanCommand(this));
        getProxy().getPluginManager().registerCommand(this, new KickCommand(this));
        getProxy().getPluginManager().registerCommand(this, new LookupCommand(this));
        getProxy().getPluginManager().registerCommand(this, new RemovenoteCommand(this));
        getProxy().getPluginManager().registerCommand(this, new UnbanCommand(this));
        super.onEnable();

        try {
            this.mcbouncer.getConfig().load();
        } catch (MCBouncerException e) {
            getLogger().log(Level.SEVERE, "Failed to load the configuration", e);
            return;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void shutdown() {
    }

    public MCBouncerPlayer getOfflinePlayer(String s) {
        return getProxy().getPlayer(s);
    }

    public MCBouncerPlayer[] getOnlinePlayers() {
        return getProxy().getPlayers();
    }

    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public void broadcast(String s, String s1) {
    }

    public void broadcast(String s) {

    }

    public MCBouncer getMCBouncerPlugin() {
        return this.mcbouncer;
    }
}
