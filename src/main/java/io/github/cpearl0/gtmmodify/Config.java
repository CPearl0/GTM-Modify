package io.github.cpearl0.gtmmodify;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = GTMModify.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<String> MODE = BUILDER
            .comment("Mode")
            .define("mode", "None");

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String mode;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        mode = MODE.get();
    }
}
