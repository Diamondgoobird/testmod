package com.diamondgoobird.mod.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class PartyReport extends CommandBase {
    @Override
    public String getCommandName() {
        return "pr";
    }
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "reports a person for typing chat in party chat";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/report " + args[0] + " -b PC_C IGR -C");
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
