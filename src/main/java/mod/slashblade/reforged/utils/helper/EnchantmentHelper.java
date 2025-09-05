package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.event.SlashBladeAttackEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class EnchantmentHelper {

    @SubscribeEvent
    public static  void onEvent(SlashBladeAttackEvent event) {

        LivingEntity attacker = event.getAttacker();
        Entity target = event.getTarget();
        ItemStack item = event.getItem();
        SlashBladeLogic slashBladeLogic = event.getSlashBladeLogic();

        // 获取Level来访问注册表
        Level level = attacker.level();
        var enchantmentRegistry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        // 获取附魔等级
        int sharpnessLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.SHARPNESS)); // 锋利
        int knockbackLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.KNOCKBACK)); // 冲击
        
        // 力量附魔的处理（根据武器类型不同）
        int powerLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.POWER)); // 力量（弓箭专用）
        int smiteLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.SMITE)); // 亡灵杀手
        int baneOfArthropodsLevel = item.getEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.BANE_OF_ARTHROPODS)); // 节肢杀手
        
        // 可以在这里添加基于附魔等级的逻辑处理
        if (sharpnessLevel > 0) {
            // 处理锋利附魔效果
            // 锋利附魔增加伤害：每级增加 0.5 * level + 0.5 伤害
        }
        
        if (knockbackLevel > 0) {
            // 处理冲击附魔效果
            // 冲击附魔增加击退距离
        }
        
        if (powerLevel > 0) {
            // 处理力量附魔效果（仅适用于弓箭）
            // 力量附魔增加弓箭伤害
        }
        
        if (smiteLevel > 0) {
            // 处理亡灵杀手附魔效果
            // 对亡灵生物造成额外伤害
        }
        
        if (baneOfArthropodsLevel > 0) {
            // 处理节肢杀手附魔效果
            // 对节肢动物造成额外伤害
        }

    }

}
