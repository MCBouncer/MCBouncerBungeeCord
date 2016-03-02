package com.mcbouncer.bungee.commands;
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

import com.mcbouncer.bungee.BungeeUtils;
import com.mcbouncer.commands.MCBouncerPluginCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BungeeMCBouncerPluginCommand extends Command {
    private MCBouncerPluginCommand mcbouncerPluginCommand;

    public BungeeMCBouncerPluginCommand(MCBouncerPluginCommand mcbouncerPluginCommand) {
        super(mcbouncerPluginCommand.getCommandName(), mcbouncerPluginCommand.getRequiredPermission()==null?null:mcbouncerPluginCommand.getRequiredPermission().toString(), mcbouncerPluginCommand.getAliases());
        this.mcbouncerPluginCommand = mcbouncerPluginCommand;
    }


    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!this.mcbouncerPluginCommand.processCommand(BungeeUtils.convertCommandSender(commandSender), strings)) {
            // TODO: Usage message
        }
    }
}
