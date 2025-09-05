package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.config.SbConfig;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.event.SlashBladeAttackEvent;
import mod.slashblade.reforged.content.event.SlashBladeDoSlashEvent;
import mod.slashblade.reforged.content.init.SbAttackTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class EnchantmentHelper {

    @SubscribeEvent
    public static void onEvent(SlashBladeAttackEvent event) {

        LivingEntity attacker = event.getAttacker();
        Entity target = event.getTarget();
        ItemStack item = event.getItem();
        SlashBladeLogic slashBladeLogic = event.getSlashBladeLogic();

        // 获取Level来访问注册表
        Level level = attacker.level();
        HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        // 锋利附魔 - 对所有目标有效
        int sharpnessLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.SHARPNESS));
        if (sharpnessLevel > 0) {
            event.addModifiedRatio(SbConfig.COMMON.sharpnessAttackBonus.get() * sharpnessLevel);
        }

        // 判定目标是否为生物实体以应用特定附魔
        if (target instanceof LivingEntity livingTarget) {
            // 亡灵杀手附魔 - 仅对亡灵生物有效
            int smiteLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.SMITE));
            if (smiteLevel > 0 && livingTarget.getType().is(EntityTypeTags.UNDEAD)) {
                event.addModifiedRatio(SbConfig.COMMON.smiteAttackBonus.get() * smiteLevel);
            }

            // 节肢杀手附魔 - 仅对节肢动物有效
            int baneOfArthropodsLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.BANE_OF_ARTHROPODS));
            if (baneOfArthropodsLevel > 0 && livingTarget.getType().is(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS)) {
                event.addModifiedRatio(SbConfig.COMMON.baneOfArthropodsAttackBonus.get() * baneOfArthropodsLevel);
            }
        }

        // 其他附魔（目前仅获取但未使用）
        //int knockbackLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.KNOCKBACK)); // 冲击

        int powerLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.POWER)); // 力量
        if (powerLevel > 0 && event.getAttackTypeList().contains(SbAttackTypes.SUMMOND_SWORD_ATTACK_TYPE.get())) {
            event.addModifiedRatio(SbConfig.COMMON.powerAttackBonus.get() * powerLevel);
        }



    }


    @SubscribeEvent
    public static void sweepingEdgeBonus(SlashBladeDoSlashEvent event) {

        Level level = event.getAttacker().level();
        HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        int smiteLevel = event.getItem().getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.SWEEPING_EDGE));
        if (smiteLevel > 0) {
            event.getSlashEffectEntity().setSize(event.getSlashEffectEntity().getSize() + (float) (SbConfig.COMMON.sweepingEdgeSizeBonus.get() * smiteLevel));
        }

    }
}
