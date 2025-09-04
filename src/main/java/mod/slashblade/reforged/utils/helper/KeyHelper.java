package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.config.SbConfig;
import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.data.capabilitie.IPlayerInputCapability;
import mod.slashblade.reforged.content.entity.LightningEntity;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.content.event.key.KeyEvent;
import mod.slashblade.reforged.content.event.key.KeyInputEvent;
import mod.slashblade.reforged.content.init.SbDataComponents;
import mod.slashblade.reforged.content.init.SbEntityType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.Random;

@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class KeyHelper {

    @SubscribeEvent
    public static void onSummoningSummondSword(KeyInputEvent keyInputEvent) {
        if (keyInputEvent.getKeyInput() != KeyInput.SUMMONING_SUMMOND_SWORD) {
            return;
        }

        if (keyInputEvent.getKeyType() != KeyInputEvent.KeyType.DOWN) {
            return;
        }

        Player player = keyInputEvent.getEntity();

        ItemStack mainHandItem = player.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponents.SLASH_BLADE_LOGIC);
        SlashBladeStyle slashBladeStyle = mainHandItem.get(SbDataComponents.SLASH_BLADE_STYLE);

        if (slashBladeLogic == null || slashBladeStyle == null) {
            return;
        }

        SummondSwordEntity summondSwordEntity = new SummondSwordEntity(SbEntityType.SUMMOND_SWORD_ENTITY.get(), player.level(), player);
        slashBladeStyle.decorate(summondSwordEntity);
        summondSwordEntity.setDamage(SbConfig.COMMON.ordinaryAttack.get());
        summondSwordEntity.setMaxLifeTime(100);
        summondSwordEntity.lookAt(SwordsmanHelper.getAttackPos(player, slashBladeLogic), false);
        player.level().addFreshEntity(summondSwordEntity);
        player.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 0.2F, 1.45F);
    }


    @SubscribeEvent
    public static void onSwordSkills(KeyInputEvent keyInputEvent) {
        if (keyInputEvent.getKeyInput() != KeyInput.SUMMONING_SUMMOND_SWORD) {
            return;
        }

        if (keyInputEvent.getKeyType() != KeyInputEvent.KeyType.HOLD) {
            return;
        }

        IPlayerInputCapability playerInputCapability = keyInputEvent.getPlayerInputCapability();

        if (!playerInputCapability.isDown(KeyInput.SNEAK)) {
            return;
        }

        Player player = keyInputEvent.getEntity();

        ItemStack mainHandItem = player.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponents.SLASH_BLADE_LOGIC);
        SlashBladeStyle slashBladeStyle = mainHandItem.get(SbDataComponents.SLASH_BLADE_STYLE);

        if (slashBladeLogic == null || slashBladeStyle == null) {
            return;
        }


        player.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 0.2F, 1.45F);

        Vec3 attackPos = SwordsmanHelper.getAttackPos(player, slashBladeLogic);

        // 剑雨
        if (playerInputCapability.isDown(KeyInput.FORWARD) && playerInputCapability.isDown(KeyInput.BACK)) {

            Vec3 offset = new Vec3(
                    SbConfig.COMMON.heavyRainOffsetX.get(),
                    SbConfig.COMMON.heavyRainOffsetY.get(),
                    SbConfig.COMMON.heavyRainOffsetZ.get()
            );
            RandomSource random = keyInputEvent.getEntity().getRandom();

            int amount = SbConfig.COMMON.heavyRainAttackNumber.get();

            for(int i = 0; i < amount; i++) {
                Vec3 pos = attackPos.add(
                        -offset.x() / 2 + random.nextDouble() * offset.x(),
                        (-offset.y() / 2 + random.nextDouble() * offset.y()) + SbConfig.COMMON.heavyRainYOffset.get(),
                        -offset.z() / 2 + random.nextDouble() * offset.z()
                );


                Vec3 singleAttackPos = attackPos.add(
                        -offset.x() / 2 + random.nextDouble() * offset.x(),
                        -offset.y() / 2 + random.nextDouble() * offset.y(),
                        -offset.z() / 2 + random.nextDouble() * offset.z()
                );
                SummondSwordEntity summondSwordEntity = new SummondSwordEntity(
                        SbEntityType.SUMMOND_SWORD_ENTITY.get(),
                        player.level(),
                        player
                );

                slashBladeStyle.decorate(summondSwordEntity);
                summondSwordEntity.setDamage(SbConfig.COMMON.heavyRainAttack.get());
                summondSwordEntity.setStartDelay(random.nextInt(10));
                summondSwordEntity.setBreakDelay(10);
                summondSwordEntity.setPos(pos.x(), pos.y(), pos.z());
                summondSwordEntity.lookAt(
                        singleAttackPos,
                        false
                );
                summondSwordEntity.setRoll(random.nextInt(360));
                player.level().addFreshEntity(summondSwordEntity);
            }

            return;
        }

        // 猛烈
        if (playerInputCapability.isDown(KeyInput.FORWARD)) {
            int amount = SbConfig.COMMON.blisteringAttackNumber.get();
            for(int i = 0; i < amount; i++) {
                SummondSwordEntity summondSwordEntity = new SummondSwordEntity(
                        SbEntityType.SUMMOND_SWORD_ENTITY.get(),
                        player.level(),
                        player
                );
                slashBladeStyle.decorate(summondSwordEntity);
                summondSwordEntity.setDamage(SbConfig.COMMON.stormSwordAttack.get());
                summondSwordEntity.setMaxLifeTime(100);
                summondSwordEntity.setStartDelay((i / 2) * 2);
                Vec3 pos = player.getEyePosition(1.0f)
                        .add(
                                VectorHelper.getVectorForRotation(
                                                0.0f,
                                                player.getViewYRot(0) + 90)
                                        .scale(
                                                i % 2 == 0
                                                        ? 1
                                                        : -1
                                        )
                        );
                summondSwordEntity.setPos(pos.x(), pos.y() + ofBlisteringOffset(i, amount), pos.z());
                summondSwordEntity.lookAt(attackPos, false);
                player.level().addFreshEntity(summondSwordEntity);
            }
            return;
        }

        // 风暴
        if (playerInputCapability.isDown(KeyInput.BACK)) {

            int amount = SbConfig.COMMON.stormSwordAttackNumber.get();
            double stepping = Math.PI * 2 / amount;

            for(int i = 0; i < amount; i++) {
                double offsetX = Math.sin(stepping * i);
                double offsetZ = Math.cos(stepping * i);
                SummondSwordEntity summondSwordEntity = new SummondSwordEntity(
                        SbEntityType.SUMMOND_SWORD_ENTITY.get(),
                        player.level(),
                        player
                );
                slashBladeStyle.decorate(summondSwordEntity);
                summondSwordEntity.setStartDelay(5);
                summondSwordEntity.setDamage(SbConfig.COMMON.stormSwordAttack.get());

                summondSwordEntity.setPos(
                        attackPos.x() + offsetX * 5,
                        attackPos.y(),
                        attackPos.z() + offsetZ * 5
                );
                summondSwordEntity.lookAt(attackPos, false);

                player.level().addFreshEntity(summondSwordEntity);
            }
            return;
        }

        // 螺旋
        {
            int amount = SbConfig.COMMON.spiralSwordAttackNumber.get();
            double stepping = Math.PI * 2 / amount;

            Vec3 pos = player.getPosition(1);

            for(int i = 0; i < amount; i++) {
                double offsetX = Math.sin(stepping * i);
                double offsetZ = Math.cos(stepping * i);
                SummondSwordEntity summondSwordEntity = new SummondSwordEntity(
                        SbEntityType.SUMMOND_SWORD_ENTITY.get(),
                        player.level(),
                        player
                );
                slashBladeStyle.decorate(summondSwordEntity);
                summondSwordEntity.setDamage(SbConfig.COMMON.spiralSwordAttack.get());
                summondSwordEntity.setStartDelay(5);
                summondSwordEntity.setPos(
                        pos.x() + offsetX * 3,
                        pos.y(),
                        pos.z() + offsetZ * 3
                );
                float yaw = player.getYRot();
                yaw = yaw / 180;
                yaw += (float) (stepping * i);
                Vec3 lookAtPosRotated = new Vec3(Math.sin(yaw), 0, Math.cos(yaw));
                summondSwordEntity.lookAt(lookAtPosRotated, true);
                player.level().addFreshEntity(summondSwordEntity);
            }
        }

    }

    public static float ofBlisteringOffset(int i, int maxAmount) {
        float maxOffset = 0.5f;
        float minOffset = -0.25f;
        float ratio = (float) i / (maxAmount - 1);
        return maxOffset - ratio * (maxOffset - minOffset);
    }

}


