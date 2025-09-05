package mod.slashblade.reforged.content.animation.event;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.animation.SlashBladeAnimationInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-02 20:23
 * @Description: TODO
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class AnimationTickHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderTick(RenderFrameEvent.Pre event) {

    }

    @SubscribeEvent
    public static void onServerTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity livingEntity){
            if (!livingEntity.level().isClientSide && !(livingEntity instanceof Player)){
                System.out.println("傻逼neoforge");
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(PlayerTickEvent.Pre event) {
        if (!event.getEntity().level().isClientSide){
            SlashBladeAnimationInstance instance = SlashBladeAnimationInstance.get(event.getEntity());
            instance.tick();
        }
    }
}
