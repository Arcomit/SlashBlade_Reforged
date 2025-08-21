package mod.slashblade.reforged;

import com.mojang.logging.LogUtils;
import mod.slashblade.reforged.content.init.SbActions;
import mod.slashblade.reforged.content.init.SbDataComponents;
import mod.slashblade.reforged.content.init.SbItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-06 14:59
 * @Description: 模组主类
 */
@Mod(SlashbladeMod.MODID)
public class SlashbladeMod {

    public static final String MODID  = "slashblade_reforged";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SlashbladeMod(IEventBus modEventBus, ModContainer modContainer) {
        SbItems.register(modEventBus);
        SbActions.register(modEventBus);
        SbDataComponents.register(modEventBus);
    }

    public static ResourceLocation prefix(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
