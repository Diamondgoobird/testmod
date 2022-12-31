package com.diamondgoobird.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.ChestRenderer;
import net.minecraft.util.ResourceLocation;
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
import org.lwjgl.opengl.DisplayMode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

@Mod(modid = TestVariables.MOD_ID, name = TestVariables.MOD_NAME, version = TestVariables.VERSION)
public class TestName {
	@Instance(TestVariables.MOD_ID)
	public static TestName instance;
	public static boolean CancelNext = false;
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) throws LWJGLException, IOException {
		TestVariables.printConsole("Starting preinit...");
		Display.setTitle(TestVariables.checkVariable("Window"));
		MinecraftForge.EVENT_BUS.register(new Test());
		//Display.sync(10);
		try {
			InputStream a = Files.newInputStream(Paths.get(TestVariables.Path + "logo.png"));
			Display.setIcon(new ByteBuffer[]{readImageToBuffer(a)});
		} catch (Exception fasd) {

		}
		MinecraftForge.EVENT_BUS.register(new TestListener());
		MinecraftForge.EVENT_BUS.register(new onJoin());
		MinecraftForge.EVENT_BUS.register(new onDisconnect());
		Display.update();
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent Event) {
		TestVariables.printConsole("Initializing Mod...");
		TestVariables.checkConfig(null);
		ClientCommandHandler.instance.registerCommand(new TestCommand());
		ClientCommandHandler.instance.registerCommand(new fovCommand());
		ClientCommandHandler.instance.registerCommand(new partyReport());
		ClientCommandHandler.instance.registerCommand(new shaderCommand());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		TestVariables.printConsole("Completing Initialization...");
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
}
