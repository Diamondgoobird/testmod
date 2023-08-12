package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.TestVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.InputStream;
import java.nio.ByteBuffer;

import static com.diamondgoobird.mod.TestName.readImageToBuffer;

@Mixin(Minecraft.class)
public class WindowChanger {

    @Redirect(method = "setWindowIcon", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setIcon([Ljava/nio/ByteBuffer;)I"))
    public int setDisplayIcon(ByteBuffer[] old_position) {
        try {
            DefaultResourcePack pack = Minecraft.getMinecraft().mcDefaultResourcePack;
            InputStream a = pack.getInputStream(new ResourceLocation("test:icon_32x32.png"));
            Display.setIcon(new ByteBuffer[]{readImageToBuffer(a)});
        } catch (Exception fasd) {
            fasd.printStackTrace();
        }
        return 0;
    }

    @Redirect(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    public void setDisplayTitle(String newTitle) {
        Display.setTitle(TestVariables.checkVariable("Window"));
    }
}
