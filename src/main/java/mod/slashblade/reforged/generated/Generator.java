package mod.slashblade.reforged.generated;

import mod.slashblade.reforged.SlashbladeMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * 主数据生成器，负责协调所有数据生成器的注册和执行
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class Generator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                true,
                new ItemStackDataPackGenerator(
                        event.getGenerator().getPackOutput(),
                        event.getLookupProvider()
                )
        );
    }


}
