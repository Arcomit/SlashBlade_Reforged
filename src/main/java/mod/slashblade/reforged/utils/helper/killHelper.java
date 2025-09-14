package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.config.SbConfig;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class killHelper {


    @SubscribeEvent
    protected static void onLivingDeathEvent(LivingDeathEvent event) {

        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (level.isClientSide()) {
            return;
        }

        Entity _attacker = event.getSource().getEntity();
        if (!(_attacker instanceof LivingEntity attacker)) {
            return;
        }

        ItemStack itemStack = attacker.getMainHandItem();
        if (itemStack.isEmpty()) {
            return;
        }

        SlashBladeLogic slashBladeLogic = itemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);
        if (slashBladeLogic == null) {
            return;
        }

        double x = attacker.getX();
        double y = attacker.getY();
        double z = attacker.getZ();

        RandomSource random = attacker.getRandom();


        // 生成耀魂碎片
        if (random.nextDouble() < SbConfig.COMMON.soulDropChance.get()) {
            ItemEntity itemEntity = new ItemEntity(
                    level, x, y, z, new ItemStack(SbItems.PROUD_SOUL.get())
            );
            level.addFreshEntity(itemEntity);
        }

        // 生成破碎的耀魂
        if (random.nextDouble() < SbConfig.COMMON.tinySoulDropChance.get()) {
            ItemEntity itemEntity = new ItemEntity(
                    level, x, y, z, new ItemStack(SbItems.PROUD_SOUL_TINY.get())
            );
            level.addFreshEntity(itemEntity);
        }

        if (random.nextDouble() < SbConfig.COMMON.enchantmentSoulDropChance.get()) {
            HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            List<Holder.Reference<Enchantment>> list = enchantmentRegistry.listElements().toList();
            Holder.Reference<Enchantment> enchantment = list.get(random.nextInt(list.size()));

            ItemStack out = new ItemStack(SbItems.PROUD_SOUL_TINY.get());
            ItemEntity itemEntity = new ItemEntity(
                    level, x, y, z, out
            );
            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            mutable.set(enchantment, 1);
            EnchantmentHelper.setEnchantments(out, mutable.toImmutable());
            level.addFreshEntity(itemEntity);
        }


        int proudSoulReward = random.nextInt(SbConfig.COMMON.proudSoulRewardMin.get(), SbConfig.COMMON.proudSoulRewardMax.get());

        itemStack.update(
                SbDataComponentTypes.SLASH_BLADE_LOGIC,
                SlashBladeLogic.DEF,
                s -> s.toBuilder()
                        .kill(s.getKill() + 1)
                        .proudSoul(s.getProudSoul() + proudSoulReward)
                        .build()
        );
    }
}
