package com.diamondgoobird.mod.commands;

import com.diamondgoobird.mod.Test;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class fovCommand extends CommandBase {
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
        if (args.length <= 0) {
        }
        else {
            Test.setFov(args[0]);
        }
    }
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
