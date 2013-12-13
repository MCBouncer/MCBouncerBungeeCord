package com.mcbouncer.bungee.command;

import com.mcbouncer.bungee.MCBouncer;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.NetworkException;
import com.mcbouncer.util.MiscUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AddgnoteCommand extends Command{

    MCBouncer plugin;

    public AddgnoteCommand(MCBouncer plugin) {
        super ("addgnote", null, "addglobalnote");
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {

            public void run() {

                if (!sender.hasPermission("mcbouncer.admin")) {
                    sender.sendMessage(ChatColor.RED + "You need permission to run that command.");
                    return;
                }

                if (args.length < 2) {
                    sender.sendMessage (ChatColor.RED + "Usage: /addgnote <player> <text>");
                    return;
                }

                String addNoteTo = args[0];
                String note = MiscUtils.join(args, " ", 1, args.length);

                boolean success = false;

                try {
                    success = plugin.api.addGlobalNote(sender.getName(), addNoteTo, note);
                } catch(NetworkException e) {
                    sender.sendMessage(ChatColor.RED + "Network timeout, could not reach mcbouncer.com");
                } catch(APIException e) {
                    sender.sendMessage(ChatColor.RED + "An API error occurred.");
                }

                if (success) {
                    sender.sendMessage("Global note added to " + addNoteTo + " successfully.");
                    plugin.getLogger().info(sender.getName() + " added a global note to " + addNoteTo + " - " + note);
                }
            }

        });
    }

}
