package com.diamondgoobird.mod;

import com.diamondgoobird.mod.commands.*;
import com.diamondgoobird.mod.listeners.TestListener;
import com.diamondgoobird.mod.listeners.OnDisconnect;
import com.diamondgoobird.mod.listeners.OnJoin;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
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

@Mod(modid = TestVariables.MOD_ID, name = TestVariables.MOD_NAME, version = TestVariables.VERSION)
public class TestName {
	@Instance(TestVariables.MOD_ID)
	public static TestName instance;
	public static boolean cancelNext = false;
	public static Logger log;
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) throws LWJGLException, IOException {
		log.info("Starting preinit...");
		Display.setTitle(TestVariables.checkVariable("Window"));
		MinecraftForge.EVENT_BUS.register(new TestListener());
		// MinecraftForge.EVENT_BUS.register(new OnJoin());
		// MinecraftForge.EVENT_BUS.register(new OnDisconnect());
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent Event) {
		log.info("Initializing Mod...");
		TestVariables.checkConfig(null);
		ClientCommandHandler.instance.registerCommand(new TestCommand());
		ClientCommandHandler.instance.registerCommand(new FovCommand());
		ClientCommandHandler.instance.registerCommand(new PartyReport());
		ClientCommandHandler.instance.registerCommand(new ShaderCommand());
		ClientCommandHandler.instance.registerCommand(new ButtonColorCommand());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		log.info("Completing Initialization...");
	}

	public static ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException
	{
		BufferedImage bufferedimage = ImageIO.read(imageStream);
		int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[])null, 0, bufferedimage.getWidth());
		ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

		for (int i : aint)
		{
			bytebuffer.putInt(i << 8 | i >> 24 & 255);
		}

		bytebuffer.flip();
		return bytebuffer;
	}

	static {
		log = Logger.getLogger("test-mod");
	}
}
