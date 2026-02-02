package com.diamondgoobird.mod.commands;

import com.diamondgoobird.mod.discord.DiscordBotClient;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;

public class DiscordCommand extends BaseCommand {
    @Override
    public String getCommandName() {
        return "discord";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        String message = args[0];
        if (message == null) {
            return;
        }
        for (int i = 1; i < args.length; i++) {
            message += " " + args[i];
        }
        // DiscordBotClient.instance.sendMessage(message);
    }
}
