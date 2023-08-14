package com.diamondgoobird.mod.commands;

import com.diamondgoobird.mod.TestConfig;
import gg.essential.api.EssentialAPI;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class TestCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "lmao just run it and find out";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		EssentialAPI.getGuiUtil().openScreen(TestConfig.instance.gui());
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public int getRequiredPermissionLevel() {
        return 0;
    }
}
