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

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeUtils {
    public static BungeeCommandSender convertCommandSender(CommandSender commandSender) {
        BungeeCommandSender cs;
        if (commandSender instanceof ProxiedPlayer) {
            cs = new BungeePlayer((ProxiedPlayer)commandSender);
        }  else {
            cs = new BungeeCommandSender(commandSender);
        }

        return cs;
    }
}
