package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.commands.AICommand;
import com.diamondgoobird.mod.commands.SendCommand;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public class GuiScreenMixin {
    @Inject(
            method = "sendChatMessage(Ljava/lang/String;Z)V",
            at = @At("HEAD")
    )
    public void onSendMessage(String msg, boolean addToChat, CallbackInfo ci) {
        AICommand.messages.remove(msg);
    }
}
