package com.diamondgoobird.mod.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedList;

public class NickSkinCommand extends BaseCommand {

    private static final LinkedList<String> skinNames = new LinkedList<>();

    static {
        try {
            for (File file : Paths.get("./skins/").toFile().listFiles()) {
                skinNames.add(file.getName().replaceAll(".png", ""));
            }
        }
        catch (Exception h) {
            h.printStackTrace();
        }
    }

    @Override
    public String getCommandName() {
        return "nickskin";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (!skinNames.isEmpty()) {
            sender.addChatMessage(new ChatComponentText("No more names!"));
            return;
        }
        String nextName = skinNames.removeFirst();
        sender.addChatMessage(new ChatComponentText(nextName).setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/nick help skin " + nextName.toUpperCase()))));
    }
}
