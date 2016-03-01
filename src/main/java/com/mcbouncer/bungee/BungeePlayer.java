package com.mcbouncer.bungee;
/*
 * MCBouncerBungee
 * Copyright 2012-2014 Deaygo Jarkko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.mcbouncer.api.MCBouncerPlayer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetAddress;
import java.util.UUID;

public class BungeePlayer extends BungeeCommandSender implements MCBouncerPlayer {

    private ProxiedPlayer player;

    public BungeePlayer(ProxiedPlayer player) {
        super(player);
        this.player = player;
    }

    public void kick(String s) {
        this.player.disconnect(new TextComponent(s));
    }

    public UUID getUniqueID() {
        return player.getUniqueId();
    }

    public InetAddress getIPAddress() {
        return player.getAddress().getAddress();
    }

    public boolean isOnline() {
        return player.isConnected();
    }
}
