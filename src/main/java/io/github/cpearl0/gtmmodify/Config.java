package io.github.cpearl0.gtmmodify;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = GTMModify.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<String> EU_MODE = BUILDER
            .comment("EU/tick Mode: None, 0EU, 1EU, Tenths")
            .define("eu_mode", "None");


    public static final ForgeConfigSpec.ConfigValue<String> TICK = BUILDER
            .comment("Recipe tick: None, 1tick, Tenths")
            .define("tick", "None");

    public static final ForgeConfigSpec.BooleanValue INPUt1 = BUILDER
            .comment("Enable input 1")
            .define("input1", false);

    public static final ForgeConfigSpec.BooleanValue OUTPUT64 = BUILDER
            .comment("Enable output 64")
            .define("output64", false);
    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String eu_mode;
    public static String tick;
    public static boolean input1;
    public static boolean output64;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        eu_mode = EU_MODE.get();
        tick = TICK.get();
        input1 = INPUt1.get();
        output64 = OUTPUT64.get();
    }
}
