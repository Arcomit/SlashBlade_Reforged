package mod.slashblade.reforged.content.client.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.client.renderer.entity.SummondSwordEntityRenderer;
import mod.slashblade.reforged.content.init.SbEntityType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = SlashbladeMod.MODID, value = Dist.CLIENT)
public class SbEntityRenderer {


    public static void register(IEventBus bus) {
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SbEntityType.SUMMOND_SWORD_ENTITY.get(), SummondSwordEntityRenderer::new);
    }

}
