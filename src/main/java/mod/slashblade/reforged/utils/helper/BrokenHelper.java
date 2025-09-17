package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.event.BrokenEvent;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.generated.group.SlashBladeItemStacks;
import mod.slashblade.reforged.utils.constant.SlashBladeNameConstants;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class BrokenHelper {

    @SubscribeEvent
    public static void onEvent(BrokenEvent event) {

        if (!event.getSlashBladeLogic().getKey().equals(SlashBladeNameConstants.NOTED_SILVER_BAMBOO_LIGHT)) {
            return;
        }

        //TODO 配置参数
        if (event.getSlashBladeLogic().getKill() < 100) {
            return;
        }

        LivingEntity user = event.getUser();
        if (user == null) {
            return;
        }

        ItemEntity itemEntity = new ItemEntity(
                user.level(), user.getX(), user.getY(), user.getZ(), SlashBladeItemStacks.SCABBARD.get()
        );
        user.level().addFreshEntity(itemEntity);

    }
}
