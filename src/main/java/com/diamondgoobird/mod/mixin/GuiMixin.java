package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.TestConfig;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Random;

@Mixin(Gui.class)
public class GuiMixin {
    private static float hue = 0.0f;
    private static final Random rand = new Random();

    @Inject(method = "drawCenteredString", at = @At("HEAD"), cancellable = true)
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color, CallbackInfo ci) {
        if (color == 16777120) {
            testmod$renderCustomText(fontRendererIn, text, x, y, ci);
        }
    }
    @Unique
    private void testmod$renderCustomText(FontRenderer fontRendererIn, String text, int x, int y, CallbackInfo ci) {
        int color = new Color(0, 251, 255).getRGB();
        try {
            ci.cancel();
            switch (TestConfig.instance.buttonType) {
                case 0:
                    try {
                        color = TestConfig.instance.buttonColor.getRGB();
                    } catch (NullPointerException ignored) {

                    }
                    break;
                case 1:
                    color = Color.HSBtoRGB(hue, 1.0f, 1.0f);
                    hue += 0.0001f;
                    break;
                case 2:
                    color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()).getRGB();
                    break;
            }
        }
        catch (Exception ignored) {

        }
        fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
    }
}
