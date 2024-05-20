package com.diamondgoobird.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class Test {
    private static float hue = 0.0F;

    static {
        new Thread( () -> {
            try {
                while (true) {
                    hue += 0.01F;
                    Thread.sleep(50);
                    if (hue > 1F) {
                        hue = hue % 1F;
                    }
                }
            }
            catch (Exception h) {

            }
        }).start();
    }

    public static synchronized float getHue() {
        return hue;
    }

    public static float getDistanceFromRod(EntityPlayer p) {
        if (p == null || p.fishEntity == null) {
            return 0;
        }
        return (float) (p.fishEntity.getDistanceSqToEntity(p) / 1024.0f);
    }

    public static void setFov(String fov) {
        try {
            Minecraft.getMinecraft().gameSettings.setOptionFloatValue(GameSettings.Options.FOV,Float.parseFloat(fov));
        } catch (Exception z) {
            TestName.print("&bFailed to get new fov value, got: &3" + fov);
        }
    }
    public static void setGamma(String gamma) {
        try {
            Minecraft.getMinecraft().gameSettings.setOptionFloatValue(GameSettings.Options.GAMMA, Float.parseFloat(gamma));
        }
        catch (Exception z) {
            TestName.print("&bFailed to get new gamma value, got: &3" + gamma);
        }
    }
    public static void setFps(String fps) {
        try {
            Minecraft.getMinecraft().gameSettings.setOptionFloatValue(GameSettings.Options.FRAMERATE_LIMIT, Float.parseFloat(fps));
        } catch (Exception h) {
            TestName.print("&bFailed to get new fps value, got: &3" + fps);
        }
    }

    public static String toPrompt(String[] args) {
        StringBuilder b = null;
        for (String s : args) {
            if (b == null) {
                b = new StringBuilder(s);
            }
            else {
                b.append(" ").append(s);
            }
        }
        return b == null ? "" : b.toString();
    }

    public void print(ICommandSender sender, String msg) {
        sender.addChatMessage(new ChatComponentText(msg));
    }

    public void print(ICommandSender sender, String s, String llama3) {
        sender.addChatMessage(new ChatComponentText(s).setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, llama3))));
    }
}
