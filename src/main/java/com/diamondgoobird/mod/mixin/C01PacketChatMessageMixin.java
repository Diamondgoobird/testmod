package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.listeners.EmoteHandler;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C01PacketChatMessage.class)
public class C01PacketChatMessageMixin {
    @Shadow private String message;

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("RETURN"))
    public void onInitReturn(String messageIn, CallbackInfo ci) {
        this.message = EmoteHandler.getEmote(messageIn);
        if (message.length() > 100) {
            message = messageIn.substring(0, 100);
        }
    }
}
