package com.mcbouncer.bungee.commands;

import com.mcbouncer.bungee.MCBouncerBungeeCord;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.NetworkException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnbanCommand extends Command {

    MCBouncerBungeeCord plugin;
    
    public UnbanCommand(MCBouncerBungeeCord plugin) {
        super("unban");
        this.plugin = plugin;
    }
    
    @Override
    public void execute(final CommandSender sender, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {

            public void run() {
                ProxiedPlayer player = null;
                
                if (!sender.hasPermission("mcbouncer.mod")) {
                    sender.sendMessage(ChatColor.RED + "You need permission to run that command.");
                    return;
                }
                
                if (sender instanceof ProxiedPlayer) {
                    player = (ProxiedPlayer)sender;
                }
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "Syntax:  /unban <username>");
                    return;
                }
                String toUnban = args[0];
                
                boolean success = false;
                try {
                    success = plugin.api.removeBan(toUnban);
                } catch (NetworkException ex) {
                    sender.sendMessage(ChatColor.RED + "Network error, could, not reach mcbouncer.com");
                } catch (APIException ex) {
                    sender.sendMessage(ChatColor.RED + ex.getMessage());
                }
                
                if (success) {
                    String message = ChatColor.GREEN + "User " + toUnban + " unbanned by " + sender.getName();
                    for (ProxiedPlayer pl : plugin.getProxy().getPlayers()) {
                        if (pl.hasPermission("mcbouncer.mod")) {
                            pl.sendMessage(message);
                        }
                    }
                }
            }
        });
    }
    
}
