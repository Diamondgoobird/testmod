package com.diamondgoobird.mod;

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
        if (!shaderCommand.on) {
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

class Shader {
    boolean post1;
    String name1;
    public Shader(boolean post, String name) {
        post1 = post;
        name1 = name;
    }
    public ResourceLocation getLocation() {
        if (post1) {
            return new ResourceLocation("shaders/post/" + name1 + ".json");
        }
        return new ResourceLocation("shaders/program/" + name1 + ".json");
    }
}

class Shaders {
    ArrayList<Shader> a = new ArrayList<Shader>(Arrays.asList(new Shader(true,"antialias"),new Shader(true,"art"),new Shader(true,"bits"),new Shader(true,"blobs"),new Shader(true,"blobs2"),new Shader(true,"blur"),new Shader(true,"bumpy"),new Shader(true,"color_convolve"),new Shader(true,"creeper"),new Shader(true,"deconverge"),new Shader(true,"desaturate"),new Shader(true,"entity_outline"),new Shader(true,"flip"),new Shader(true,"fxaa"),new Shader(true,"green"),new Shader(true,"invert"),new Shader(true,"notch"),new Shader(true,"ntsc"),new Shader(true,"outline"),new Shader(true,"pencil"),new Shader(true,"phosphor"),new Shader(true,"scan_pincushion"),new Shader(true,"sobel"),new Shader(true,"spider"),new Shader(true,"wobble"),new Shader(false,"antialias"),new Shader(false,"bits"),new Shader(false,"blit"),new Shader(false,"blobs"),new Shader(false,"blobs2"),new Shader(false,"blur"),new Shader(false,"bumpy"),new Shader(false,"color_convolve"),new Shader(false,"deconverge"),new Shader(false,"downscale"),new Shader(false,"entity_outline"),new Shader(false,"flip"),new Shader(false,"fxaa"),new Shader(false,"invert"),new Shader(false,"notch"),new Shader(false,"ntsc_decode"),new Shader(false,"ntsc_encode"),new Shader(false,"outline"),new Shader(false,"outline_combine"),new Shader(false,"outline_soft"),new Shader(false,"outline_watercolor"),new Shader(false,"overlay"),new Shader(false,"phosphor"),new Shader(false,"scan_pincushion"),new Shader(false,"sobel"),new Shader(false,"spider"),new Shader(false,"wobble")));
    public boolean contains(String check) {
        for (Shader b : a) {
            if (b.name1.equalsIgnoreCase(check)) {
                return true;
            }
        }
        return false;
    }
    public boolean isPost(String check) {
        for (Shader b : a) {
            if (b.post1) {
                return true;
            }
        }
        return false;
    }
    public String[] getShaderNames(boolean post) {
        ArrayList<String> c = new ArrayList<>();
        for (Shader b : a) {
            if (b.post1 == post) {
                c.add(b.name1);
            }
        }
        return c.toArray(new String[0]);
    }
}
