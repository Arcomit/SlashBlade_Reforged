package mod.slashblade.reforged.content.client.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.client.renderer.entity.EntityNameRenderer;
import mod.slashblade.reforged.content.client.renderer.entity.LightningEntityRenderer;
import mod.slashblade.reforged.content.client.renderer.entity.SummondSwordEntityRenderer;
import mod.slashblade.reforged.content.init.SbEntityType;
import net.minecraft.client.renderer.LevelRenderer;
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


    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SbEntityType.SUMMOND_SWORD_ENTITY.get(), EntityNameRenderer::new); //TODO
        event.registerEntityRenderer(SbEntityType.DRIVE_ENTITY.get(), EntityNameRenderer::new); //TODO
        event.registerEntityRenderer(SbEntityType.SLASH_EFFECT_ENTITY.get(), EntityNameRenderer::new); //TODO
        event.registerEntityRenderer(SbEntityType.JUDGEMENT_CUT_ENTITY.get(), EntityNameRenderer::new); //TODO
        event.registerEntityRenderer(SbEntityType.LIGHTNING_ENTITY.get(), LightningEntityRenderer::new);
    }

}
