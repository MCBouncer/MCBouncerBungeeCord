package com.mcbouncer.bungee;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.YamlConfig;
import com.mcbouncer.api.MCBouncerImplementation;
import com.mcbouncer.api.MCBouncerPlayer;
import com.mcbouncer.bungee.commands.*;
import com.mcbouncer.bungee.listener.ProxiedPlayerListener;
import com.mcbouncer.commands.*;
import com.mcbouncer.exceptions.MCBouncerException;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;

public class MCBouncerBungeeCord extends Plugin implements MCBouncerImplementation {

    public MCBouncer mcbouncer;

    @Override
    public void onEnable() {
        this.mcbouncer = new MCBouncer(this, new YamlConfig(this));
        getProxy().getPluginManager().registerListener(this, new ProxiedPlayerListener(this));
        getProxy().getPluginManager().registerCommand(this, new BungeeGlobalNoteCommand(new GlobalNoteCommand(this)));
        getProxy().getPluginManager().registerCommand(this, new BungeeNoteCommand(new NoteCommand(this)));
        getProxy().getPluginManager().registerCommand(this, new BungeeBanCommand(new BanCommand(this)));
        getProxy().getPluginManager().registerCommand(this, new BungeeKickCommand(new KickCommand(this)));
        getProxy().getPluginManager().registerCommand(this, new BungeeLookupCommand(new LookupCommand(this)));
        getProxy().getPluginManager().registerCommand(this, new BungeeRemoveNoteCommand(new RemoveNoteCommand(this)));
        getProxy().getPluginManager().registerCommand(this, new BungeeUnbanCommand(new UnbanCommand(this)));
        getProxy().getPluginManager().registerCommand(this, new BungeeTimedBanCommand(new TimedBanCommand(this)));
        getProxy().getPluginManager().registerCommand(this, new BungeeMCBouncerPluginCommand(new MCBouncerPluginCommand(this)));

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
        return null;
    }

    public MCBouncerPlayer[] getOnlinePlayers() {
        Collection<MCBouncerPlayer> online = new HashSet();
        for (ProxiedPlayer p : getProxy().getPlayers()) {
            online.add(new BungeePlayer(p));
        }

        return (MCBouncerPlayer[]) online.toArray();
    }

    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public void broadcast(String permission, String message) {
        for (ProxiedPlayer p : getProxy().getPlayers()) {
            if (p.hasPermission(permission)) {
                p.sendMessage(ChatMessageType.SYSTEM, BungeeUtils.translateMessage(message));
            }
        }
    }

    public void broadcast(String message) {
        getProxy().broadcast(BungeeUtils.translateMessage(message));
    }

    public MCBouncer getMCBouncerPlugin() {
        return this.mcbouncer;
    }
}
