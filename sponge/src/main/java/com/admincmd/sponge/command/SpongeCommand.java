/*
 * This file is part of AdminCMD
 * Copyright (C) 2015 AdminCMD Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/
package com.admincmd.sponge.command;

import com.admincmd.api.AdminCMD;
import com.admincmd.api.command.Command;
import com.admincmd.api.command.parsing.Arguments;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SpongeCommand implements CommandCallable {

    private Command command;

    public SpongeCommand(Command command) {
        this.command = command;
    }

    @Override
    public CommandResult process(CommandSource sender, String args) throws CommandException {
        com.admincmd.api.command.CommandSource source = new SpongeCommandSource(sender);
        Arguments arguments = new Arguments(args.split(" "));

        AdminCMD.getCommandManager().callCommand(command, source, arguments);
        return CommandResult.success();
    }

    @Override
    public List<String> getSuggestions(CommandSource sender, String args) throws CommandException {
        return Collections.emptyList();
    }

    @Override
    public boolean testPermission(CommandSource commandSource) {
        return true;
    }

    @Override
    public Optional<? extends Text> getShortDescription(CommandSource commandSource) {
        Text text = Text.of(command.getDescription());
        return Optional.of(text);
    }

    @Override
    public Optional<? extends Text> getHelp(CommandSource commandSource) {
        Text text = Text.of(command.getDescription());
        return Optional.of(text);
    }

    @Override
    public Text getUsage(CommandSource commandSource) {
        return Text.of(command.getUsage());
    }
}
