package mod.slashblade.reforged.content.animation.event;

import mod.slashblade.reforged.SlashbladeMod;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-31 15:21
 * @Description: TODO
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class TestEvent {

    @SubscribeEvent
    public static void test(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        float walkDelta = player.walkDist - player.walkDistO;
        if (walkDelta > 0.05F) {
            if (player.isSprinting()){
                System.out.println("Sprinting");
                return;
            }
            System.out.println("Walking");
        }else {
            System.out.println("Standing");
        }
    }
}
