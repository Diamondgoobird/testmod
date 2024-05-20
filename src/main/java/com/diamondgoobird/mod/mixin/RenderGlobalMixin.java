package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.Test;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(RenderGlobal.class)
public class RenderGlobalMixin {
    @Redirect(method = "drawSelectionBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    public void colorSelectionBox(float colorRed, float colorGreen, float colorBlue, float colorAlpha) {
        Color c = Color.getHSBColor(Test.getHue(), 1.0F, 1.0F);
        float[] c1 = c.getColorComponents(null);
        GlStateManager.color(c1[0], c1[1], c1[2], 1.0F);
    }
}
