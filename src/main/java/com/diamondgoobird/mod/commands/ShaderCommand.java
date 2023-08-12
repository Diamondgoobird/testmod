package com.diamondgoobird.mod.commands;

import com.diamondgoobird.mod.shader.Shader;
import com.diamondgoobird.mod.shader.Shaders;
import com.diamondgoobird.mod.TestVariables;
import com.diamondgoobird.mod.shader.ShaderListener;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class ShaderCommand extends CommandBase {
    private static boolean on = false;

    public static boolean isOn() {
        return on;
    }

    @Override
    public String getCommandName() {return "shader";}

    @Override
    public String getCommandUsage(ICommandSender sender) {return "Lets you choose which shader you want to use.";}

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        try {
            if (args[0].equalsIgnoreCase("set")) {
                Shader c = new Shader(args[1], args[2]);
                try {
                    Minecraft.getMinecraft().entityRenderer.loadShader(c.getLocation());
                } catch (Exception q) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Failed to load shader \"" + c.getLocation().toString() + "\""));
                }
            } else if (args[0].equalsIgnoreCase("time")) {
                TestVariables.changeVariable("Shader Time", args[1]);
                try {
                    ShaderListener.time = (int) (240 * Double.parseDouble(args[1]));
                } catch (NumberFormatException error) {
                    TestVariables.print("Failed to get number from: " + args[1]);
                }
            } else if (args[0].equalsIgnoreCase("type")) {
                TestVariables.changeVariable("Shader Type", args[1]);
            } else if (args[0].equalsIgnoreCase("toggle")) {
                if (isOn() && args.length > 2) {
                    MinecraftForge.EVENT_BUS.unregister(new ShaderListener());
                    Minecraft.getMinecraft().entityRenderer.stopUseShader();
                } else {
                    MinecraftForge.EVENT_BUS.register(new ShaderListener());
                }
                on = !on;
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Shader: " + isOn()));
            }
        }
        catch (ArrayIndexOutOfBoundsException exception) {
            TestVariables.print("&cInvalid argument amount");
        }
    }
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {return true;}

    @Override
    public int getRequiredPermissionLevel() {return 0;}

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        String[] list;
        if (args.length <= 1) {
            list = new String[]{"set","time","type","toggle"};
        }
        else if (args[0].equalsIgnoreCase("time")) {
            list = new String[]{"1.0","5.0","10.0","30.0"};
        }
        else if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 2) {
                list = new String[]{"post","program"};
            }
            else {
                list = Shaders.getShaderNames(args[1]);
            }
        }
        else if (args[0].equalsIgnoreCase("type")) {
            list = new String[]{"all","post","program"};
        }
        else {
            return null;
        }
        return getListOfStringsMatchingLastWord(args, list);
    }
}