package com.diamondgoobird.mod.mixin;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(method = "drawCenteredString", at = @At("HEAD"), cancellable = true)
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color, CallbackInfo ci) {
        if (color == 16777120) {
            ci.cancel();
            color = new Color(0, 251, 255).getRGB();
            fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
        }
    }
}
