package com.mcbouncer.bungee.command;

import com.mcbouncer.bungee.MCBouncer;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.NetworkException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class RemovenoteCommand extends Command {

    MCBouncer plugin;

    public RemovenoteCommand(MCBouncer plugin) {
        super("removenote", null, "delnote");
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {

        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable(){

            public void run() {

                if(!sender.hasPermission("mcbouncer.mod")) {
                    sender.sendMessage(ChatColor.RED + "You need permission to run that command.");
                    return;
                }

                if(args.length < 1) {
                    sender.sendMessage(ChatColor.RED + "Usage: /delnote <note id>");
                    return;
                }

                int noteId;

                try {
                    noteId = Integer.parseInt(args[0]);
                } catch(NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "The note ID must be a number!");
                    return;
                }
                
                boolean success = false;
                try {
                    success = plugin.api.removeNote(noteId, sender.getName());
                } catch(NetworkException e) {
                    sender.sendMessage("Network error, could not reach mcbouncer.com.");
                } catch(APIException e) {
                    sender.sendMessage("An API error occurred.");
                }

                if(success) {
                    sender.sendMessage(ChatColor.GREEN + "Note removed successfully.");
                    plugin.getLogger().info("Note ID " + args[0] + " was removed by " + sender.getName() + ".");
                }
            }

        });

    }

}
