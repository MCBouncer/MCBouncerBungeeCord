package com.mcbouncer.bungee;
/*
 * MCBouncerBungee
 * Copyright 2012-2016 Deaygo Jarkko
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

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
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

    public static BaseComponent[] translateMessage(String message) {
        ComponentBuilder builder = new ComponentBuilder("");
        String chars="01234567890abcdeflkmnor";
        StringBuilder str = new StringBuilder("");
        ChatColor color = null;
        boolean bold = false;
        boolean underline = false;
        boolean strikethrough = false;
        boolean italic = false;
        boolean reset = false;
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '&' && i+1 < message.length() && chars.indexOf(message.charAt(i+1)) >= 0) {
                if (str.length() > 0) {
                    ComponentBuilder tmp = builder.append(str.toString());

                    if (reset) {
                        tmp.reset();
                    }

                    if (color != null) {
                        tmp.color(color);
                    }

                    tmp.bold(bold).underlined(underline).strikethrough(strikethrough).italic(italic);

                    str = new StringBuilder("");
                }

                switch(message.charAt(i+1)) {
                    case '0': color = ChatColor.BLACK; break;
                    case '1': color = ChatColor.DARK_BLUE; break;
                    case '2': color = ChatColor.DARK_GREEN; break;
                    case '3': color = ChatColor.DARK_AQUA; break;
                    case '4': color = ChatColor.DARK_RED; break;
                    case '5': color = ChatColor.DARK_PURPLE; break;
                    case '6': color = ChatColor.GOLD; break;
                    case '7': color = ChatColor.GRAY; break;
                    case '8': color = ChatColor.DARK_GRAY; break;
                    case '9': color = ChatColor.BLUE; break;
                    case 'a': color = ChatColor.GREEN; break;
                    case 'b': color = ChatColor.AQUA; break;
                    case 'c': color = ChatColor.RED; break;
                    case 'd': color = ChatColor.LIGHT_PURPLE; break;
                    case 'e': color = ChatColor.YELLOW; break;
                    case 'f': color = ChatColor.WHITE; break;
                    case 'l': bold = true; break;
                    case 'k': color = ChatColor.MAGIC; break;
                    case 'm': strikethrough = true; break;
                    case 'n': underline = true; break;
                    case 'o': italic = true; break;
                    case 'r':
                        reset = true;
                        bold = false;
                        underline = false;
                        italic = false;
                        strikethrough = false;
                        break;
                }
                i++;
            }
            else {
                str.append(message.charAt(i));
            }
        }


        if (str.length() > 0) {
            ComponentBuilder tmp = builder.append(str.toString());

            if (reset) {
                tmp.reset();
            }

            if (color != null) {
                tmp.color(color);
            }
            tmp.bold(bold).underlined(underline).strikethrough(strikethrough).italic(italic);

        }

        return builder.create();
    }
}
