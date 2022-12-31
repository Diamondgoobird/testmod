package com.diamondgoobird.mod;

import net.minecraft.block.Block;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Test {
    public static void setFov(String fov) {
        try {
            Minecraft.getMinecraft().gameSettings.setOptionFloatValue(GameSettings.Options.FOV,Float.parseFloat(fov));
        } catch (Exception z) {
            TestVariables.print("&bFailed to get new fov value, got: &3" + fov);
        }
    }
    public static void setGamma(String gamma) {
        try {
            Minecraft.getMinecraft().gameSettings.setOptionFloatValue(GameSettings.Options.GAMMA, Float.parseFloat(gamma));
        }
        catch (Exception z) {
            TestVariables.print("&bFailed to get new gamma value, got: &3" + gamma);
        }
    }
    public static void setFps(String fps) {
            try {
                Minecraft.getMinecraft().gameSettings.setOptionFloatValue(GameSettings.Options.FRAMERATE_LIMIT, Float.parseFloat(fps));
            } catch (Exception h) {
                TestVariables.print("&bFailed to get new fps value, got: &3" + fps);
            }
        }

    public static void test() throws LWJGLException {
        Minecraft.getMinecraft().getIntegratedServer().stopServer();
        Display.makeCurrent();
    }

}
