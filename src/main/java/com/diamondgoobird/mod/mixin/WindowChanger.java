package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.TestName;
import com.diamondgoobird.mod.TestVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static com.diamondgoobird.mod.TestName.readImageToBuffer;

@Mixin(Minecraft.class)
public class WindowChanger {

    @Redirect(method = "setWindowIcon", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setIcon([Ljava/nio/ByteBuffer;)I"))
    public int setDisplayIcon(ByteBuffer[] old_position) {
        if (!testmod$setCustomIcon()) {
            testmod$setLunarIcon();
        }
        return 0;
    }

    @Unique
    private void testmod$setLunarIcon() {
        try {
            DefaultResourcePack pack = Minecraft.getMinecraft().mcDefaultResourcePack;
            InputStream a = pack.getInputStream(new ResourceLocation("test:icon_32x32.png"));
            Display.setIcon(new ByteBuffer[]{readImageToBuffer(a)});
        } catch (Exception fasd) {
            fasd.printStackTrace();
        }
    }

    @Unique
    private boolean testmod$setCustomIcon() {
        try {
            InputStream a = Files.newInputStream(Paths.get(TestVariables.Path + "logo.png"));
            Display.setIcon(new ByteBuffer[]{readImageToBuffer(a)});
            return true;
        } catch (IOException fasd) {
            TestName.log.info("Couldn't find custom icon, using default...");
            return false;
        }
    }

    @Redirect(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    public void setDisplayTitle(String newTitle) {
        Display.setTitle(TestVariables.checkVariable("Window"));
    }
}
