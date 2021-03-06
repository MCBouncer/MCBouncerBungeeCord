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
import com.mcbouncer.commands.UnbanCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BungeeUnbanCommand extends Command {
    private UnbanCommand unbanCommand;

    public BungeeUnbanCommand(UnbanCommand unbanCommand) {
        super(unbanCommand.getCommandName(), unbanCommand.getRequiredPermission().toString(), unbanCommand.getAliases());
        this.unbanCommand = unbanCommand;
    }


    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        this.unbanCommand.processCommand(BungeeUtils.convertCommandSender(commandSender), strings);
    }
}
