package io.github.cpearl0.gtmmodify.mixin.gtceu;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(GTRecipe.class)
public class GTRecipeMixin {

    //tick
    @Redirect(method = "<init>(Lcom/gregtechceu/gtceu/api/recipe/GTRecipeType;Lnet/minecraft/resources/ResourceLocation;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/List;Ljava/util/List;Lnet/minecraft/nbt/CompoundTag;IZ)V ", at = @At(value = "FIELD", target = "Lcom/gregtechceu/gtceu/api/recipe/GTRecipe;duration:I", opcode = Opcodes.PUTFIELD), remap = false)
    private void duration(GTRecipe instance, int value){
        if (value > 1){
            instance.duration = 1;
        }
    }
    @Inject(method = "<init>(Lcom/gregtechceu/gtceu/api/recipe/GTRecipeType;Lnet/minecraft/resources/ResourceLocation;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/List;Ljava/util/List;Lnet/minecraft/nbt/CompoundTag;IZ)V ", at = @At(value = "TAIL")   , remap = false)
    private void tickInputEUt(GTRecipeType recipeType, ResourceLocation id, Map inputs, Map<RecipeCapability<?>, List<Content>> outputs, Map<RecipeCapability<?>, List<Content>>  tickInputs, Map tickOutputs, Map inputChanceLogics, Map outputChanceLogics, Map tickInputChanceLogics, Map tickOutputChanceLogics, List conditions, List ingredientActions, CompoundTag data, int duration, boolean isFuel, CallbackInfo ci){




        //EU
        tickInputs.forEach((k, v) -> {
            if (k instanceof EURecipeCapability) {
                v.forEach((ticks) -> {
                    ticks.content = (Long) ticks.content > 0L ? 0L : ticks.content;
                });
            }
        });

        //output max
        outputs.forEach((k, v) -> {
            if (k instanceof ItemRecipeCapability){
                v.forEach((items) -> {
                    SizedIngredient i = (SizedIngredient)items.content;
                    ArrayList<ItemStack> _items = new ArrayList<>();
                    for (ItemStack item : i.getInner().getItems()) {
                        item.setCount(item.getMaxStackSize());
                        _items.add(item);
                    }
                    items.content = SizedIngredient.create(Ingredient.of(_items.toArray(ItemStack[]::new)), i.getAmount());

                });
            }
        });
    }

}
