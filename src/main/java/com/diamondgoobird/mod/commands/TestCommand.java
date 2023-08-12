package com.diamondgoobird.mod.commands;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.diamondgoobird.mod.Test;
import com.diamondgoobird.mod.TestName;
import com.diamondgoobird.mod.TestVariables;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.lwjgl.opengl.Display;

public class TestCommand extends CommandBase {
	public final String[] options = {"help","enable","disable","debug","variable","fov","fps","gamma","window"};
	public final String help = "";
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return TestVariables.CommandName;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return help;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length <= 0) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Please try again (Type '/"+ TestVariables.CommandName + " help' for options)"));
			return;
        }
		
		else if (args[0].equalsIgnoreCase(options[0])) {
			TestVariables.print(help);
		}
		
		else if (args[0].equalsIgnoreCase(options[1])) {
			TestVariables.changeVariable("Toggle", "Enabled");
		}
		
		else if (args[0].equalsIgnoreCase(options[2])) {
			TestVariables.changeVariable("Toggle", "Disabled");
		}
		else if (args[0].equalsIgnoreCase(options[3])) {
			debug();
		}
		else if (args[0].equalsIgnoreCase(options[4])) {
			TestVariables.changeVariable(args[1], args[2]);
			debug();
		}
		else if (args[0].equalsIgnoreCase(options[5])) {
			Test.setFov(args[1]);
		}
		else if (args[0].equalsIgnoreCase(options[6])) {
			Test.setFps(args[1]);
		}
		else if (args[0].equalsIgnoreCase(options[7])) {
			Test.setGamma(args[1]);
		}
		else if (args[0].equalsIgnoreCase(options[8])) {
			String window = args[1];
			int x = 2;
			if (args.length > 1) {
				while (args.length > x) {
					window = window + " " + args[x];
					x++;
				}
			}
			TestVariables.changeVariable("Window",window);
			Display.setTitle(TestVariables.checkVariable("Window"));
			try {
				InputStream a = Files.newInputStream(Paths.get(TestVariables.Path + "icon.png"));
				Display.setIcon(new ByteBuffer[]{TestName.readImageToBuffer(a)});
			} catch (Exception fasd) {

			}
		}
	}
	
	public static void debug() {
		String[] stuff = TestVariables.checkConfig(null);
		String[] things = TestVariables.things;
		String output = "";
		int x = 0;
		while (stuff.length > x) {
			String comma = ", ";
			if (stuff.length == x + 1) {
				comma = "";
			}
			output = output + things[x] + ": " + stuff[x] + comma;
			x++;
		}
		TestVariables.print(output);
		TestName.log.info(output);
	
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, options): null;
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
