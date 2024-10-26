package io.github.cpearl0.gtmmodify.mixin.gtceu;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import io.github.cpearl0.gtmmodify.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(GTRecipe.class)
public class GTRecipeMixin {


    @Inject(method = "<init>(Lcom/gregtechceu/gtceu/api/recipe/GTRecipeType;Lnet/minecraft/resources/ResourceLocation;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/List;Ljava/util/List;Lnet/minecraft/nbt/CompoundTag;IZ)V ", at = @At(value = "TAIL")   , remap = false)
    private void modify(GTRecipeType recipeType, ResourceLocation id, Map<RecipeCapability<?>, List<Content>> inputs, Map<RecipeCapability<?>, List<Content>> outputs, Map<RecipeCapability<?>, List<Content>>  tickInputs, Map<RecipeCapability<?>, List<Content>> tickOutputs, Map<RecipeCapability<?>, ChanceLogic> inputChanceLogics, Map<RecipeCapability<?>, ChanceLogic> outputChanceLogics, Map<RecipeCapability<?>, ChanceLogic> tickInputChanceLogics, Map<RecipeCapability<?>, ChanceLogic> tickOutputChanceLogics, List<RecipeCondition> conditions, List<?> ingredientActions, CompoundTag data, int duration, boolean isFuel, CallbackInfo ci){
        GTRecipe instance = (GTRecipe) (Object) this;

        //tick
        if(Objects.equals(Config.tick, "1tick") && instance.duration > 1) {
            instance.duration = 1;
        } else if (Objects.equals(Config.tick, "Tenths")){
            instance.duration /= 10;
            instance.duration = Math.max(1, instance.duration);
        }
        //EU
        if(!Objects.equals(Config.eu_mode, "None")) {
            tickInputs.forEach((k, v) -> {
                if (k instanceof EURecipeCapability) {
                    v.forEach((ticks) -> {
                        if(Objects.equals(Config.eu_mode, "0EU")){
                            ticks.content = (Long) ticks.content > 0L ? 0L : ticks.content;
                        }else if(Objects.equals(Config.eu_mode, "1EU")) {
                            ticks.content = (Long) ticks.content > 1L ? 1L : ticks.content;
                        } else if (Objects.equals(Config.eu_mode, "Tenths")) {
                            ticks.content = (Long) ticks.content > 0L ? ((Long) ticks.content / 10) : ticks.content;
                        }
                    });
                }
            });
        }

        //input 1
        if(Config.input1) {
            inputs.forEach((k, v) -> {
                if (k instanceof ItemRecipeCapability) {
                    v.forEach((items) -> {
                        var i = items.content;
                        if (i instanceof SizedIngredient) {
                            ArrayList<ItemStack> _items = new ArrayList<>(Arrays.asList(((SizedIngredient) i).getInner().getItems()));
                            items.content = SizedIngredient.create(Ingredient.of(_items.toArray(ItemStack[]::new)), 1);
                        }

                    });
                } else if (k instanceof FluidRecipeCapability) {
                    v.forEach((fluids) -> {
                        FluidIngredient i = (FluidIngredient) fluids.content;
                        i.setAmount(1);
                    });
                }
            });
        }


        //output max
        if(Config.output64) {
            outputs.forEach((k, v) -> {
                if (k instanceof ItemRecipeCapability) {
                    v.forEach((items) -> {
                        SizedIngredient i = (SizedIngredient) items.content;
                        ArrayList<ItemStack> _items = new ArrayList<>();
                        int maxCount = Integer.MAX_VALUE;
                        for (ItemStack item : i.getInner().getItems()) {
                            //item.setCount(item.getMaxStackSize());
                            _items.add(item);
                            if (item.getMaxStackSize() < maxCount) {
                                maxCount = item.getMaxStackSize();
                            }
                        }
                        items.content = SizedIngredient.create(Ingredient.of(_items.toArray(ItemStack[]::new)), Math.max(maxCount, i.getAmount()));

                    });
                } else if (k instanceof FluidRecipeCapability) {
                    v.forEach((fluids) -> {
                        FluidIngredient i = (FluidIngredient) fluids.content;
                        var amo = i.getAmount();
                        long newAmo = amo;
                        if (amo % 1000 == 0) {
                            newAmo = 16000;
                        } else {
                            newAmo = 144 * 64;
                        }
                        i.setAmount(newAmo / 2);

                    });
                }
            });
        }
    }

}
