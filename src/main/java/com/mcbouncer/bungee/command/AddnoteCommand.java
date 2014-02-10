package com.mcbouncer.bungee.command;

import com.mcbouncer.bungee.MCBouncer;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.NetworkException;
import com.mcbouncer.util.MiscUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AddnoteCommand extends Command {

    MCBouncer plugin;

    public AddnoteCommand(MCBouncer plugin) {
        super ("addnote");
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {

            public void run() {

                if (!sender.hasPermission("mcbouncer.mod")) {
                    sender.sendMessage(ChatColor.RED + "You need permission to run that command.");
                    return;
                }

                if (args.length < 2) {
                    sender.sendMessage (ChatColor.RED + "Usage: /addnote <player> <text>");
                    return;
                }

                String addNoteTo = args[0];
                String note = MiscUtils.join(args, " ", 1, args.length);

                boolean success = false;

                try {
                    success = plugin.api.addNote(sender.getName(), addNoteTo, note);
                } catch(NetworkException e) {
                    sender.sendMessage(ChatColor.RED + "Network timeout, could not reach mcbouncer.com");
                } catch(APIException e) {
                    sender.sendMessage(ChatColor.RED + "An API error occurred.");
                }

                if (success) {
                    sender.sendMessage("Note added to " + addNoteTo + " successfully.");
                }
            }

        });
    }

}
