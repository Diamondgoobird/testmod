package com.diamondgoobird.mod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = net.minecraftforge.fml.common.FMLCommonHandler.class, remap = false)
public class FMLCommonHandlerMixin {
    @Inject(method = "getBrandings", at = @At("HEAD"), cancellable = true)
    public void getBrandings(boolean includeMC, CallbackInfoReturnable<List<String>> cir) {
        cir.setReturnValue(new ArrayList<>());
    }
}
