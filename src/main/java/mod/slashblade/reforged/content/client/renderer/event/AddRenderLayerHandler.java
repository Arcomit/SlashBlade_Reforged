package mod.slashblade.reforged.content.client.renderer.event;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.client.renderer.item.layer.SlashBladeThirdPersonLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-19 10:34
 * @Description: 添加渲染层处理类
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = SlashbladeMod.MODID)
public class AddRenderLayerHandler {

    @SubscribeEvent
    public static void addRenderLayer(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model playerModel : event.getSkins()){
            EntityRenderer<? extends Player> renderer = event.getSkin(playerModel);
            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new SlashBladeThirdPersonLayer(livingRenderer));
            }
        }


        for (EntityType<?> type : event.getEntityTypes()) {
            EntityRenderer<?> renderer = event.getRenderer(type);
            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new SlashBladeThirdPersonLayer(livingRenderer));
            }
        }
    }
}
