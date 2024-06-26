package com.diamondgoobird.mod.mixin;

import com.diamondgoobird.mod.TestConfig;
import com.diamondgoobird.mod.TestName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.diamondgoobird.mod.TestName.readImageToBuffer;

@Mixin(Minecraft.class)
public class WindowChanger {
    @Shadow
    private static Minecraft theMinecraft;

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
            DefaultResourcePack pack = theMinecraft.mcDefaultResourcePack;
            InputStream a = pack.getInputStream(new ResourceLocation("test:icon_32x32.png"));
            Display.setIcon(new ByteBuffer[]{readImageToBuffer(a)});
        } catch (Exception fasd) {
            fasd.printStackTrace();
        }
    }

    @Unique
    private boolean testmod$setCustomIcon() {
        try {
            String e = new File("config/icon.png").getAbsolutePath();
            TestName.log.info(e);
            InputStream a = Files.newInputStream(Paths.get(e));
            Display.setIcon(new ByteBuffer[]{readImageToBuffer(a)});
            return true;
        } catch (IOException fasd) {
            TestName.log.info("Couldn't find custom icon, using default...");
            return false;
        }
    }

    @Redirect(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    public void setDisplayTitle(String newTitle) {
        Display.setTitle(TestConfig.instance.windowName);
    }
}
