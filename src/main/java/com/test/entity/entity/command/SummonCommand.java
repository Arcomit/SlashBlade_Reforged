package com.test.entity.entity.command;

import mod.slashblade.reforged.SlashbladeMod;
import com.test.entity.entity.TestEntity;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-09 01:31
 * @Description: TODO
 */
@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class SummonCommand {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("summontest")
                .executes(context -> {
                    Player player = context.getSource().getPlayerOrException();
                    TestEntity projectile = new TestEntity(player, player.level());
                    projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                    player.level().addFreshEntity(projectile);

                    return 1;
                })
        );
    }
}
