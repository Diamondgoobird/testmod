package com.diamondgoobird.mod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TestListener {
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String text = event.message.getFormattedText();
        if (text.contains("unclaimed leveling reward")) {
            event.setCanceled(true);
        }
        else if (text.startsWith("\u00a7eClick here")) {
            TestName.CancelNext = false;
            event.setCanceled(true);
        }
    }
    /*@SubscribeEvent
    public static void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
        new Thread(() -> {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lang english");
        }).start();
    }*/
}
