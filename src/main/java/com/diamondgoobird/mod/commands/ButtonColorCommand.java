package com.diamondgoobird.mod.commands;

import com.diamondgoobird.mod.TestVariables;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class ButtonColorCommand extends BaseCommand {
    @Override
    public String getCommandName() {
        return "buttoncolor";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "changes the color of all button text";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1) {
            switch (args[0].toLowerCase()) {
                case "set":
                    if (args.length == 2) {
                        TestVariables.setColor(args[1]);
                    }
                    else if (args.length == 4) {
                        TestVariables.setColor(args[1], args[2], args[3]);
                    }
                    break;
                case "type":
                    TestVariables.changeVariable("Color Type", args[1]);
                    TestVariables.checkConfig(null);
                    break;
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        String[] tabcomplete = new String[]{};
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("type")) {
                tabcomplete = new String[]{"static", "rainbow", "random"};
            }
            else if (args[0].equalsIgnoreCase("set")) {
                TestVariables.print("&fType any hex color code (ex: #&cFF&aFF&9FF&f) or &cR&aG&9B&f with spaces between, (ex: &c255 &a255 &9255)");
            }
        }
        else if (args.length <= 1) {
            tabcomplete = new String[]{"set", "type"};
        }
        return getListOfStringsMatchingLastWord(args, tabcomplete);
    }
}
