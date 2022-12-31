package com.diamondgoobird.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import scala.tools.nsc.ScalaDoc;

import java.util.List;

public class partyReport extends CommandBase {
    @Override
    public String getCommandName() {
        return "pr";
    }
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "report idiots on skyblock";
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
