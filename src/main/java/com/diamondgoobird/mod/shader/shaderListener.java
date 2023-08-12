package com.diamondgoobird.mod.shader;

import com.diamondgoobird.mod.TestVariables;
import com.diamondgoobird.mod.commands.shaderCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class shaderListener {
    public static File shaders = new File(TestVariables.Path + TestVariables.MOD_ID + "_shaders.txt");
    public static int tick = 0;
    public static int amount = 0;
    public static int time = 320;
    public static boolean warned = false;

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event) {
        if (!shaderCommand.isOn()) {
            return;
        }
        if (tick >= time) {
            amount++;
            try {
                try {
                    loadShader(TestVariables.checkVariable("Shader Type"));
                }
                catch (Exception k) {
                    if (!warned) {
                        warned = true;
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("You don't have a proper shader type selected, so we are using the default option."));
                    }
                    Minecraft.getMinecraft().entityRenderer.activateNextShader();
                }
            }
            catch (Exception q) {
                q.printStackTrace();
            }
            tick = 0;
        }
        else {
            tick++;
        }
    }

    public static void loadShader(String variant) {
        EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
        ArrayList<ResourceLocation> resources = new ArrayList<>();
        if (variant.equals("all") || variant.equals("post") || variant.equals("program") || variant.contains("custom.")) {
            resources = getResourceLocations(variant);
        }
        renderer.loadShader(resources.get(new Random().nextInt(resources.size())));
    }
    public static ArrayList<ResourceLocation> getResourceLocations(String type) {
        ArrayList<ResourceLocation> list = new ArrayList<>();
        String post = "shaders/post/";
        String program = "shaders/program/";
        ArrayList<String> postShaders = new ArrayList<String>(Arrays.asList("antialias","art","bits","blobs","blobs2","blur","color_convolve","creeper","desaturate","flip","green","invert","ntsc","pencil","phosphor","scan_pincushion","sobel","spider","wobble"));
        ArrayList<String> allPostShaders = new ArrayList<String>(Arrays.asList("antialias","art","bits","blobs","blobs2","blur","bumpy","color_convolve","creeper","deconverge","desaturate","entity_outline","flip","fxaa","green","invert","notch","ntsc","outline","pencil","phosphor","scan_pincushion","sobel","spider","wobble"));
        ArrayList<String> programShaders = new ArrayList<String>(Arrays.asList("blit","bumpy","deconverge","downscale","entity_outline","fxaa","notch","ntsc_decode","ntsc_encode","outline","outline_combine","outline_soft","outline_watercolor","overlay"));
        ArrayList<String> allProgramShaders = new ArrayList<String>(Arrays.asList("antialias","bits","blit","blobs","blobs2","blur","bumpy","color_convolve","deconverge","downscale","entity_outline","flip","fxaa","invert","notch","ntsc_decode","ntsc_encode","outline","outline_combine","outline_soft","outline_watercolor","overlay","phosphor","scan_pincushion","sobel","spider","wobble"));
        ArrayList<String> color = new ArrayList<String>(Arrays.asList());
        ArrayList<String> noColor = new ArrayList<String>(Arrays.asList());
        if (type.equals("post")) {
            for (String name : allPostShaders) {
                list.add(new ResourceLocation(post + name + ".json"));
            }
        }
        else if (type.equals("program")) {
            for (String name : allProgramShaders) {
                list.add(new ResourceLocation(program + name + ".json"));
            }
        }
        else if (type.equals("all")) {
            for (String name : programShaders) {
                list.add(new ResourceLocation(program + name + ".json"));
            }
            for (String name : postShaders) {
                list.add(new ResourceLocation(post + name + ".json"));
            }
        }
        else if (type.contains("custom.")) {
            try {
                if (!shaders.exists()) {
                    FileWriter b = new FileWriter(shaders);
                    b.write("Please format your shaders in the format:\n<post/program>:<shadername>\n");
                }
                FileReader a = new FileReader(shaders);
            }
            catch (Exception q) {

            }
        }
        return list;
    }
}