package com.mcbouncer.bungee;

import com.mcbouncer.Perm;
import com.mcbouncer.api.MCBouncerCommandSender;
import net.md_5.bungee.api.CommandSender;

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
public class BungeeCommandSender implements MCBouncerCommandSender {
    CommandSender sender;
    public BungeeCommandSender(CommandSender sender) {
        this.sender = sender;
    }
    public String getName() {
        return sender.getName();
    }

    public Boolean hasPermission(Perm perm) {
        return sender.hasPermission(perm.toString());
    }

    public void sendMessage(String s) {
        sender.sendMessage(s);
    }
}
