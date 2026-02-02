package com.diamondgoobird.mod;

import com.diamondgoobird.mod.commands.*;
import com.diamondgoobird.mod.listeners.OnKey;
import com.diamondgoobird.mod.listeners.TestListener;
import com.diamondgoobird.mod.shader.ShaderListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

@Mod(modid = TestName.MOD_ID, name = TestName.MOD_NAME, version = TestName.VERSION)
public class TestName {
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
	public static final String MOD_ID = "test";
	public static final String MOD_NAME = "Diamond's test mod";
	public static final String VERSION = "1.0";
	public static boolean cancelNext = false;
	public static Logger log;
    public static final KeyBinding SEND_MESSAGE = new KeyBinding("Send Chatbot message", Keyboard.KEY_Y,  MOD_ID);
    public static final KeyBinding REJECT_MESSAGE = new KeyBinding("Reject Chatbot message", Keyboard.KEY_N,  MOD_ID);
    public static final KeyBinding VIEW_MESSAGES = new KeyBinding("View queued messages", Keyboard.KEY_H, MOD_ID);

	static {
		log = Logger.getLogger("test-mod");
	}
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) throws LWJGLException, IOException {
		new Thread( () -> {
			Scanner scan = new Scanner(System.in);
			while (true) {
                try {
					Minecraft.getMinecraft().getNetHandler().handleChat(new S02PacketChat(new ChatComponentText(scan.nextLine())));
                    Thread.sleep(1500);
                } catch (Exception e) {

                }
                // new TestListener().onChat(new ClientChatReceivedEvent((byte) 0, new ChatComponentText(scan.nextLine())));
				// ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/rat " + scan.nextLine());
			}
		}).start();
		log.info("Starting preinit...");
		Display.setTitle(TestConfig.instance.windowName);
		MinecraftForge.EVENT_BUS.register(new TestListener());
		MinecraftForge.EVENT_BUS.register(new ShaderListener());
        MinecraftForge.EVENT_BUS.register(new OnKey());
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
		ClientCommandHandler.instance.registerCommand(new DownloadSkinCommand());
		ClientCommandHandler.instance.registerCommand(new NickSkinCommand());
		//ClientCommandHandler.instance.registerCommand(new DiscordCommand());
		ClientCommandHandler.instance.registerCommand(new AICommand());
		ClientCommandHandler.instance.registerCommand(new ScoreboardCommand());
        ClientRegistry.registerKeyBinding(SEND_MESSAGE);
        ClientRegistry.registerKeyBinding(REJECT_MESSAGE);
        ClientRegistry.registerKeyBinding(VIEW_MESSAGES);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		log.info("Completing Initialization...");
		// DiscordBotClient instance = DiscordBotClient.instance;
		// log.info(instance != null ? "Completed discord initialization" : "Failed discord initialization");
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

	public static ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
		BufferedImage bufferedimage = ImageIO.read(imageStream);
		int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[])null, 0, bufferedimage.getWidth());
		ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

		for (int i : aint) {
			bytebuffer.putInt(i << 8 | i >> 24 & 255);
		}

		bytebuffer.flip();
		return bytebuffer;
	}

	public static void downloadSkin(String name, String fileName) {
		for (NetworkPlayerInfo networkPlayerInfo : Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
			if (networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(name)) {
				downloadSkinFromProfile(networkPlayerInfo.getGameProfile(), fileName);
			}
		}
	}

	private static void downloadSkinFromProfile(GameProfile profile, String fileName) {
		Property p = profile.getProperties().get("textures").toArray(new Property[0])[0];
		String json = new String(Base64.decodeBase64(p.getValue()), Charsets.UTF_8);
		MinecraftTexturesPayload result = GSON.fromJson(json, MinecraftTexturesPayload.class);
		try {
			URL skinUrl = new URL(result.getTextures().get(MinecraftProfileTexture.Type.SKIN).getUrl());
			HttpURLConnection connection = (HttpURLConnection) skinUrl.openConnection();
			InputStream s = connection.getInputStream();
			//
			BufferedImage i = TextureUtil.readBufferedImage(s);
			if (i.getHeight() == 32) {
				i = resizeImage(i);
				PipedOutputStream pos = new PipedOutputStream();
				s = new PipedInputStream(pos);
				BufferedImage finalI = i;
				// might not need to be a thread?
				new Thread(() -> {
					try {
						ImageIO.write(finalI, "png", pos);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}).start();
			}
			//
			File directory = Paths.get("./skins/").toFile();
			directory.mkdir();
			File file = new File(directory.getPath() + "/" + fileName.toLowerCase() + ".png");
			OutputStream os = Files.newOutputStream(file.toPath());
			byte[] b = new byte[2048];
			int total;
			while ((total = s.read(b)) != -1) {
				os.write(b, 0, total);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage resizeImage(BufferedImage originalImage) {
		BufferedImage resizedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.setBackground(new Color(0, 0, 0, 0));
		g.drawImage(originalImage, 0, 0, 64, 32, null);
		// make an image for each arm and leg, then draw it like the line above to be in the right spot
		BufferedImage armImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ag = armImage.createGraphics();
		ag.setBackground(new Color(0, 0, 0, 0));
		ag.drawImage(originalImage, 0, -16, 64, 32, null);
		flipComponents(armImage);

		BufferedImage legImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D lg = legImage.createGraphics();
		lg.setBackground(new Color(0, 0, 0, 0));
		lg.drawImage(originalImage, -40, -16, 64, 32, null);
		flipComponents(legImage);

		lg.dispose();
		ag.dispose();

		g.drawImage(armImage, 16, 48, 16, 16, null);
		g.drawImage(legImage, 32, 48, 16, 16, null);

		g.dispose();
		return resizedImage;
	}

	private static void flipComponents(BufferedImage image) {
		Graphics2D graphics2D = image.createGraphics();
		BufferedImage[] parts = getParts(image);
		BufferedImage p2 = parts[2];
		parts[2] = parts[4];
		parts[4] = p2;
		for (int i = 0; i < 6; i++) {
			int x = 4 * (i < 2 ? i + 1 : i - 2);
			int y = i < 2 ? 0 : 4;
			int w = 4;
			int h = i < 2 ? 4 : 12;
			graphics2D.drawImage(parts[i], x, y, w, h, null);
		}
	}

	private static BufferedImage[] getParts(BufferedImage image) {
		BufferedImage[] parts = new BufferedImage[6];
		for (int i = 0; i < 6; i++) {
			parts[i] = flip(image.getSubimage(4 * (i < 2 ? i + 1 : i - 2), i < 2 ? 0 : 4, 4, i < 2 ? 4 : 12));
		}
		return parts;
	}

	private static BufferedImage flip(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = flippedImage.createGraphics();

		g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
		g.dispose();

		return flippedImage;
	}
}
