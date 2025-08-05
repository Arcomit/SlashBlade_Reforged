package mod.slashblade.reforged;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(SlashbladeMod.MODID)
public class SlashbladeMod {
    public static final String MODID  = "slashblade_reforged";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SlashbladeMod(IEventBus modEventBus, ModContainer modContainer) {

    }

    public static ResourceLocation prefix(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
