package com.diamondgoobird.mod.mixin;

import net.minecraft.client.gui.GuiChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiChat.class)
public class GuiChatMixin {
    @ModifyArg(
            method = "initGui",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;setMaxStringLength(I)V"), index = 0
    )
    public int getLength(int p_146203_1_) {
        return 1000;
    }
}
