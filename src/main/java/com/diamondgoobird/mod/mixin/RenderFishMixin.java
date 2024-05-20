package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.Test;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.entity.projectile.EntityFishHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(RenderFish.class)
public class RenderFishMixin {
    @Redirect(
            method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/WorldRenderer;color(IIII)Lnet/minecraft/client/renderer/WorldRenderer;"
            )
    )
    public WorldRenderer redirectChangeColor(WorldRenderer instance, int i, int red, int green, int blue) {
        return instance;
    }

    @Inject(
            method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/WorldRenderer;color(IIII)Lnet/minecraft/client/renderer/WorldRenderer;"
            )
    )
    public void afterChangeColor(EntityFishHook entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        float distance = Test.getDistanceFromRod(entity.angler);
        float[] c = Color.getHSBColor(distance * 0.75f, 1.0f, 1.0f).getColorComponents(null);
        Tessellator.getInstance().getWorldRenderer().color(c[0], c[1], c[2], 1.0f);
    }
}
