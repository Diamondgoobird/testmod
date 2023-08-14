package com.diamondgoobird.mod.shader;

import com.diamondgoobird.mod.TestConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ShaderListener {
    public static int tick = 0;
    public static int amount = 0;
    public static boolean warned = false;

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event) {
        if (TestConfig.instance.switchShaders) {
            if (tick >= TestConfig.instance.shaderTime * 20) {
                amount++;
                try {
                    try {
                        loadShader(TestConfig.instance.shaderType);
                    } catch (Exception k) {
                        if (!warned) {
                            warned = true;
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("You don't have a proper shader type selected, so we are using the default option."));
                        }
                        Minecraft.getMinecraft().entityRenderer.activateNextShader();
                    }
                } catch (Exception q) {
                    q.printStackTrace();
                }
                tick = 0;
            } else {
                tick++;
            }
        }
    }

    public static void loadShader(int variant) {
        EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
        ArrayList<ResourceLocation> resources;
        resources = getResourceLocations(variant);
        renderer.loadShader(resources.get(new Random().nextInt(resources.size())));
    }
    public static ArrayList<ResourceLocation> getResourceLocations(int type) {
        ArrayList<ResourceLocation> list = new ArrayList<>();
        String post = "shaders/post/";
        String program = "shaders/program/";
        ArrayList<String> postShaders = new ArrayList<String>(Arrays.asList("antialias","art","bits","blobs","blobs2","blur","color_convolve","creeper","desaturate","flip","green","invert","ntsc","pencil","phosphor","scan_pincushion","sobel","spider","wobble"));
        ArrayList<String> allPostShaders = new ArrayList<String>(Arrays.asList("antialias","art","bits","blobs","blobs2","blur","bumpy","color_convolve","creeper","deconverge","desaturate","entity_outline","flip","fxaa","green","invert","notch","ntsc","outline","pencil","phosphor","scan_pincushion","sobel","spider","wobble"));
        ArrayList<String> programShaders = new ArrayList<String>(Arrays.asList("blit","bumpy","deconverge","downscale","entity_outline","fxaa","notch","ntsc_decode","ntsc_encode","outline","outline_combine","outline_soft","outline_watercolor","overlay"));
        ArrayList<String> allProgramShaders = new ArrayList<String>(Arrays.asList("antialias","bits","blit","blobs","blobs2","blur","bumpy","color_convolve","deconverge","downscale","entity_outline","flip","fxaa","invert","notch","ntsc_decode","ntsc_encode","outline","outline_combine","outline_soft","outline_watercolor","overlay","phosphor","scan_pincushion","sobel","spider","wobble"));
        ArrayList<String> color = new ArrayList<String>(Arrays.asList());
        ArrayList<String> noColor = new ArrayList<String>(Arrays.asList());
        if (type == 0) {
            for (String name : programShaders) {
                list.add(new ResourceLocation(program + name + ".json"));
            }
            for (String name : postShaders) {
                list.add(new ResourceLocation(post + name + ".json"));
            }
        }
        else if (type == 1) {
            for (String name : allPostShaders) {
                list.add(new ResourceLocation(post + name + ".json"));
            }
        }
        else if (type == 2) {
            for (String name : allProgramShaders) {
                list.add(new ResourceLocation(program + name + ".json"));
            }
        }
        return list;
    }
}