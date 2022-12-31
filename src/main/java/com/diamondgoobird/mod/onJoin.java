package com.diamondgoobird.mod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class onJoin {
    public static boolean joined = false;
    @SubscribeEvent
    public void chatEvent(ClientChatReceivedEvent event) {
        if (joined != true) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lang english");
            joined = true;
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
