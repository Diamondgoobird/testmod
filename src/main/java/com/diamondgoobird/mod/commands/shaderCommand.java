package com.diamondgoobird.mod.commands;

import com.diamondgoobird.mod.shader.Shader;
import com.diamondgoobird.mod.shader.Shaders;
import com.diamondgoobird.mod.TestVariables;
import com.diamondgoobird.mod.shader.shaderListener;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class shaderCommand extends CommandBase {
    public static Shader[] shaders;
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
        if (args[0].equalsIgnoreCase("set")) {
            Shader c = new Shader(false,"");
            if (args[1].equalsIgnoreCase("post")) {
                c = new Shader(true,args[2]);
            }
            else if (args[1].equalsIgnoreCase("program")) {
                c = new Shader(false,args[2]);
            }
            try {
               Minecraft.getMinecraft().entityRenderer.loadShader(c.getLocation());
            }
            catch (Exception q) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Failed to load shader \"" + c.getLocation().toString() + "\""));
            }
        }
        else if (args[0].equalsIgnoreCase("time")) {
            TestVariables.changeVariable("Shader Time", args[1]);
            try {
                shaderListener.time = (int) (240 * Double.parseDouble(args[1]));
            }
            catch (Exception q) {
                q.printStackTrace();
            }
        }
        else if (args[0].equalsIgnoreCase("type")) {
            TestVariables.changeVariable("Shader Type", args[1]);
        }
        else if (args[0].equalsIgnoreCase("toggle")) {
            if (isOn()) {
                on = false;
                MinecraftForge.EVENT_BUS.unregister(new shaderListener());
                Minecraft.getMinecraft().entityRenderer.stopUseShader();
            }
            else {
                on = true;
                MinecraftForge.EVENT_BUS.register(new shaderListener());
            }
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Shader: " + isOn()));
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
            else if (args[1].equalsIgnoreCase("post")) {
                list = new Shaders().getShaderNames(true);
            }
            else if (args[1].equalsIgnoreCase("program")) {
                list = new Shaders().getShaderNames(false);
            }
            else {
                return null;
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