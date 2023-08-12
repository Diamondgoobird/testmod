package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.TestName;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends Gui {
    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V"))
    public void drawString(GuiMainMenu instance, FontRenderer fontRenderer, String s, int i1, int i2, int i3) {
        if (!s.equals("Copyright Mojang AB. Do not distribute!")) {
            instance.drawString(fontRenderer, s, i1, i2, i3);
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiMainMenu;splashText:Ljava/lang/String;", opcode = Opcodes.GETFIELD))
    public String getSplashText(GuiMainMenu instance) {
        return "";
    }
}
