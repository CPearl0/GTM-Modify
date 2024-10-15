package io.github.cpearl0.gtmmodify.mixin;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GTRecipeBuilder.class, remap = false)
public abstract class GTRecipeBuilderMixin {
    @Shadow
    public int duration;

    @Inject(method = "buildRawRecipe", at = @At("HEAD"))
    public void modifyRecipe(CallbackInfoReturnable<GTRecipe> cir) {
        if (duration > 24)
            duration = 24;
    }
}
