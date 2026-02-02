package com.diamondgoobird.mod.listeners;

import com.diamondgoobird.mod.TestName;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class OnKey {
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (TestName.VIEW_MESSAGES.isPressed()) {
            ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/ai !send view");
        }
        if (TestName.SEND_MESSAGE.isPressed()) {
            ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/ai !send");
        }
        if (TestName.REJECT_MESSAGE.isPressed()) {
            ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/ai !send reject");
        }
    }
}
