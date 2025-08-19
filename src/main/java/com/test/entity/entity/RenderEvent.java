package com.test.entity.entity;

import mod.slashblade.reforged.SlashbladeMod;
import com.test.entity.entity.init.EntityInits;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-09 02:09
 * @Description: TODO
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID, value = Dist.CLIENT)
public class RenderEvent {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInits.BLACK_DICK.get(), TestRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BlackModel.LAYER_LOCATION, BlackModel::createBodyLayer);
    }
}
