
package com.mcbouncer.bungee.listener;

import com.mcbouncer.api.MCBouncerPlayerLoginEvent;
import com.mcbouncer.bungee.MCBouncerBungeeCord;

import java.net.InetAddress;
import java.util.UUID;

import com.mcbouncer.listeners.PlayerListener;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxiedPlayerListener implements Listener {

    private MCBouncerBungeeCord plugin;
    private PlayerListener mcbPlayerListener;
    
    public ProxiedPlayerListener(MCBouncerBungeeCord plugin) {
        this.plugin = plugin;
        this.mcbPlayerListener = new PlayerListener(plugin);
    }
    
    @EventHandler
    public void onPlayerJoin(final LoginEvent event) {

        event.registerIntent(plugin);
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {

            public void run() {
                try {
                    String username = event.getConnection().getName();
                    InetAddress ip = event.getConnection().getAddress().getAddress();
                    UUID uuid = event.getConnection().getUniqueId();

                    MCBouncerPlayerLoginEvent mcbEvent = new MCBouncerPlayerLoginEvent(username, ip, uuid);
                    mcbPlayerListener.onPlayerLogin(mcbEvent);

                    if (mcbEvent.isDisallowed()) {
                        event.setCancelled(true);
                        event.setCancelReason(mcbEvent.getKickMessage());
                    }
                }
                finally {
                    event.completeIntent(plugin);
                }
            }
        });
    }
}
