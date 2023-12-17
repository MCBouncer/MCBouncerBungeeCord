package com.mcbouncer.bungee.listener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.mcbouncer.bungee.MCBouncer;
import com.mcbouncer.bungee.command.AddnoteCommand;
import com.mcbouncer.bungee.command.BanCommand;
import com.mcbouncer.bungee.command.KickCommand;
import com.mcbouncer.bungee.command.LookupCommand;
import com.mcbouncer.bungee.command.RemovenoteCommand;
import com.mcbouncer.bungee.command.UnbanCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {

    private MCBouncer plugin;

    public PluginMessageListener(MCBouncer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginMessage(final PluginMessageEvent event) {
    	if (!event.getTag().equalsIgnoreCase("BungeeCord")) {
    		return;
		}

    	ProxiedPlayer sender = (ProxiedPlayer) event.getReceiver();

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
		try {
			String channel = in.readUTF();
			if (channel.equalsIgnoreCase("MCBouncer")) {
		        if (!sender.hasPermission("mcbouncer.mod")) {
		            sender.sendMessage(ChatColor.RED + "You need permission to run that command.");
		            return;
		        }

				String command = in.readUTF();
				if (command.equalsIgnoreCase("kick")) {
					String toKick = in.readUTF();
					String reason = in.readUTF();
	                ProxiedPlayer p = plugin.getProxy().getPlayer(toKick);
	                if (p != null) {
	                	new KickCommand(plugin).execute(sender, new String[]{ p.getName(), reason });
	                } else {
	                	sender.sendMessage(toKick + " not found");
	                }
				} else if (command.equalsIgnoreCase("ban")) {
					String toBan = in.readUTF();
					String reason = in.readUTF();
					new BanCommand(plugin).execute(sender, new String[]{ toBan, reason });
				} else if (command.equalsIgnoreCase("unban")) {
					String player = in.readUTF();
					new UnbanCommand(plugin).execute(sender, new String[]{ player });
				} else if (command.equalsIgnoreCase("lookup")) {
					String player = in.readUTF();
					new LookupCommand(plugin).execute(sender, new String[]{ player });
				} else if (command.equalsIgnoreCase("addnote")) {
					String player = in.readUTF();
					String note = in.readUTF();
					new AddnoteCommand(plugin).execute(sender, new String[]{ player, note });
				} else if (command.equalsIgnoreCase("delnote")) {
					int note_id = Integer.parseInt(in.readUTF());
					new RemovenoteCommand(plugin).execute(sender, new String[]{ Integer.toString(note_id) });
				}
			}
		} catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "An IOException Occured.");
        }
    }
}