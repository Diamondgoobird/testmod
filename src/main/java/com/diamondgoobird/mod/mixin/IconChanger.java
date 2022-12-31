package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.TestVariables;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.diamondgoobird.mod.TestName.readImageToBuffer;

@Mixin(Minecraft.class)
public class IconChanger {
    @Inject(method = "setWindowIcon", at = @At("RETURN"))
    private void setWindowIcon(CallbackInfo info) {
        try {
            InputStream a = Files.newInputStream(Paths.get(TestVariables.Path + "logo.png"));
            Display.setIcon(new ByteBuffer[]{readImageToBuffer(a)});
        } catch (Exception fasd) {
            fasd.printStackTrace();
        }
    }
}
