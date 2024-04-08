package com.diamondgoobird.mod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.ChatComponentText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C0DPacketCloseWindow.class)
public class ClosePacketMixin {
    @Shadow
    private int windowId;

    @Inject(method = "<init>()V", at = @At(value = "RETURN"))
    public void onStart(CallbackInfo ci) {
        try {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.valueOf(windowId)));
        }
        catch (Exception h) {

        }
    }
}
