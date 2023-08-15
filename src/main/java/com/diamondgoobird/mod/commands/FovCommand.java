package com.diamondgoobird.mod.commands;

import com.diamondgoobird.mod.Test;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class FovCommand extends BaseCommand {
    @Override
    public String getCommandName() {
        return "fov";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Changes your fov";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1) {
            Test.setFov(args[0]);
        }
    }
}
