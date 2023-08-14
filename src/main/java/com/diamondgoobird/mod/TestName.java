package com.diamondgoobird.mod;

import com.diamondgoobird.mod.commands.*;
import com.diamondgoobird.mod.listeners.TestListener;
import com.diamondgoobird.mod.shader.ShaderListener;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

@Mod(modid = TestName.MOD_ID, name = TestName.MOD_NAME, version = TestName.VERSION)
public class TestName {
	public static final String MOD_ID = "test";
	public static final String MOD_NAME = "Diamond's test mod";
	public static final String VERSION = "1.0";
	public static boolean cancelNext = false;
	public static Logger log;
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) throws LWJGLException, IOException {
		log.info("Starting preinit...");
		Display.setTitle(TestConfig.instance.windowName);
		MinecraftForge.EVENT_BUS.register(new TestListener());
		MinecraftForge.EVENT_BUS.register(new ShaderListener());
		// MinecraftForge.EVENT_BUS.register(new OnJoin());
		// MinecraftForge.EVENT_BUS.register(new OnDisconnect());
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent Event) {
		log.info("Initializing Mod...");
		TestConfig.instance.initialize();
		ClientCommandHandler.instance.registerCommand(new TestCommand());
		ClientCommandHandler.instance.registerCommand(new FovCommand());
		ClientCommandHandler.instance.registerCommand(new PartyReport());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		log.info("Completing Initialization...");
	}

	public static void print(String input) {
		ChatComponentText e = new ChatComponentText(input);
		char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'k', 'l', 'm', 'n', 'o', 'r'};
		int x = 0;
		int b = 22;
		while (x < b) {
			e = new ChatComponentText(e.getUnformattedText().replaceAll("&" + a[x], "\u00a7" + a[x]));
			e = new ChatComponentText(e.getUnformattedText().replaceAll("&&", "&"));

			x++;
		}
		Minecraft.getMinecraft().thePlayer.addChatMessage(e);
	}

	public static ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException
	{
		BufferedImage bufferedimage = ImageIO.read(imageStream);
		int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[])null, 0, bufferedimage.getWidth());
		ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

		for (int i : aint) {
			bytebuffer.putInt(i << 8 | i >> 24 & 255);
		}

		bytebuffer.flip();
		return bytebuffer;
	}

	static {
		log = Logger.getLogger("test-mod");
	}
}
