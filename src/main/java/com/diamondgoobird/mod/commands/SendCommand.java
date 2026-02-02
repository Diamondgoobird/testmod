package com.diamondgoobird.mod.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.LinkedList;
import java.util.Queue;

public class SendCommand extends BaseCommand {
    /*public static Queue<String> messages = new LinkedList<>();*/

    @Override
    public String getCommandName() {
        return "send";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        /*String message = messages.remove();
        if (args.length < 1 || args[0].equalsIgnoreCase("yes") || args[0].equalsIgnoreCase("y")) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
        }*/
    }
}
