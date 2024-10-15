package io.github.cpearl0.gtmmodify.mixin;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GTRecipeSerializer.class, remap = false)
public abstract class GTRecipeSerializerMixin {
    @Inject(method = "fromJson(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonObject;)Lcom/gregtechceu/gtceu/api/recipe/GTRecipe;", at = @At("RETURN"), cancellable = true)
    public void modifyRecipe(CallbackInfoReturnable<GTRecipe> cir) {
        var recipe = cir.getReturnValue();
        if (recipe.duration > 24)
            recipe.duration = 24;
        cir.setReturnValue(recipe);
    }
}
