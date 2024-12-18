package io.github.cpearl0.gtmmodify;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;
import org.slf4j.Logger;

@Mod(GTMModify.MODID)
public class GTMModify {
    public static final String MODID = "gtmmodify";
    private static final Logger LOGGER = LogUtils.getLogger();

    public GTMModify(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        context.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
        context.registerDisplayTest(new IExtensionPoint.DisplayTest(IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
    }
}
