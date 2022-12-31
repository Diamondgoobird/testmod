package com.diamondgoobird.mod.mixin;

import net.minecraft.entity.monster.EntityCreeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityCreeper.class)
public class otherMixinThatWillWork {
    @Shadow
    private int timeSinceIgnited;
    @Inject(method = "onUpdate", at = @At("RETURN"))
    public void onUpdate(CallbackInfo info)
    {
        if (this.timeSinceIgnited >= 5)
        {
            this.timeSinceIgnited = 5;
            this.explode();
        }
    }
    @Shadow
    private void explode() {}
}
