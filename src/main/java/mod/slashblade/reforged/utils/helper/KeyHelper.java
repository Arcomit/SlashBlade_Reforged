package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.entity.LightningEntity;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.content.event.key.KeyEvent;
import mod.slashblade.reforged.content.event.key.KeyInputEvent;
import mod.slashblade.reforged.content.init.SbDataComponents;
import mod.slashblade.reforged.content.init.SbEntityType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class KeyHelper {

    @SubscribeEvent
    public static void onEvent(KeyInputEvent keyInputEvent) {
        if (keyInputEvent.getKeyInput() != KeyInput.SUMMONING_SUMMOND_SWORD) {
            return;
        }

        if (keyInputEvent.getKeyType() != KeyInputEvent.KeyType.DOWN) {
            return;
        }

        Player player = keyInputEvent.getEntity();

        ItemStack mainHandItem = player.getMainHandItem();
        SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponents.SLASH_BLADE_LOGIC);
        if (slashBladeLogic == null) {
            return;
        }

        SummondSwordEntity summondSwordEntity = new SummondSwordEntity(SbEntityType.SUMMOND_SWORD_ENTITY.get(), player.level(), player);
        summondSwordEntity.setDamage(0.2f); //TODO 从配置加载
        summondSwordEntity.setMaxLifeTime(100);
        summondSwordEntity.lookAt(SwordsmanHelper.getAttackPos(player, slashBladeLogic), false);
        summondSwordEntity.attackActionCallbackPoint.register(e -> {
            LightningEntity lightningEntity = new LightningEntity(SbEntityType.LIGHTNING_ENTITY.get(), player.level(), player);
            lightningEntity.setDamage(0.2f); //TODO 从配置加载
            lightningEntity.setPos(e.getX(), e.getY(), e.getZ());
            player.level().addFreshEntity(lightningEntity);
        });
        player.level().addFreshEntity(summondSwordEntity);
        player.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 0.2F, 1.45F);
    }

}


