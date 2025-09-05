package mod.slashblade.reforged.content.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: til
 * @Description: SlashBlade Reforged 模组配置
 */
public class SbConfig {
    public static final ModConfigSpec COMMON_CONFIG;
    public static final Common COMMON;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_CONFIG = builder.build();
    }

    public static class Common {
        // Entity targeting configuration
        public final ModConfigSpec.ConfigValue<Boolean> allowInvulnerable;
        public final ModConfigSpec.ConfigValue<Boolean> isLivingEntity;
        public final ModConfigSpec.ConfigValue<Boolean> friendlyFire;
        public final ModConfigSpec.ConfigValue<Boolean> skipAttackChecks;

        // Anvil configuration
        public final ModConfigSpec.ConfigValue<Double> seCost;
        public final ModConfigSpec.ConfigValue<Double> enchantmentCost;

        // Attack configuration
        public final ModConfigSpec.ConfigValue<Double> refineAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> rankMaxAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> thousandKillAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> tenThousandKillAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> thousandRefineAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> tenThousandRefineAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> durabilityLoss;
        public final ModConfigSpec.ConfigValue<Double> durabilityReductionRate;

        // Copy SA configuration
        public final ModConfigSpec.ConfigValue<Double> getSuccessRate;
        public final ModConfigSpec.ConfigValue<Double> lossRefine;
        public final ModConfigSpec.ConfigValue<Double> minRefine;
        public final ModConfigSpec.ConfigValue<Double> minKill;

        // Dropped item configuration
        public final ModConfigSpec.ConfigValue<Double> soulDropChance;
        public final ModConfigSpec.ConfigValue<Double> tinySoulDropChance;
        public final ModConfigSpec.ConfigValue<Double> enchantmentSoulDropChance;
        public final ModConfigSpec.ConfigValue<Double> enchantmentSoulSuccessRate;
        public final ModConfigSpec.ConfigValue<Double> entityDropChance;

        // Slash effect configuration
        public final ModConfigSpec.ConfigValue<Boolean> useBlockParticle;

        // Slayer style arts configuration
        public final ModConfigSpec.ConfigValue<Double> sprintAttack;
        public final ModConfigSpec.ConfigValue<Double> sprintPower;

        // Summoned sword configuration
        public final ModConfigSpec.ConfigValue<Double> ordinaryAttack;
        public final ModConfigSpec.ConfigValue<Double> blisteringAttack;
        public final ModConfigSpec.ConfigValue<Integer> blisteringAttackNumber;
        public final ModConfigSpec.ConfigValue<Double> heavyRainAttack;
        public final ModConfigSpec.ConfigValue<Integer> heavyRainAttackNumber;
        public final ModConfigSpec.ConfigValue<Double> heavyRainYOffset;
        public final ModConfigSpec.ConfigValue<Double> heavyRainOffsetX;
        public final ModConfigSpec.ConfigValue<Double> heavyRainOffsetY;
        public final ModConfigSpec.ConfigValue<Double> heavyRainOffsetZ;
        public final ModConfigSpec.ConfigValue<Double> stormSwordAttack;
        public final ModConfigSpec.ConfigValue<Integer> stormSwordAttackNumber;
        public final ModConfigSpec.ConfigValue<Double> spiralSwordAttack;
        public final ModConfigSpec.ConfigValue<Integer> spiralSwordAttackNumber;

        // UP SE configuration
        public final ModConfigSpec.ConfigValue<Double> probabilityFactor;

        // Preload configuration
        public final ModConfigSpec.ConfigValue<Boolean> preload;

        // Enchantment configuration
        public final ModConfigSpec.ConfigValue<Double> sharpnessAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> smiteAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> baneOfArthropodsAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> powerAttackBonus;
        public final ModConfigSpec.ConfigValue<Double> sweepingEdgeSizeBonus;



        Common(ModConfigSpec.Builder builder) {
            // Entity targeting configuration
            builder.comment("Entity targeting configuration")
                    .push("targeting");

            allowInvulnerable = builder
                    .comment("Whether to allow targeting invulnerable entities")
                    .define("allow_invulnerable", true);

            isLivingEntity = builder
                    .comment("Whether to only target living entities")
                    .define("living_entity_only", true);

            friendlyFire = builder
                    .comment("Whether to allow friendly fire (attacking allied entities)")
                    .define("friendly_fire", true);

            skipAttackChecks = builder
                    .comment("Whether to skip canAttack checks")
                    .define("skip_attack_checks", true);

            builder.pop();

            // Anvil configuration
            builder.comment("Anvil related configuration")
                    .push("anvil");

            seCost = builder
                    .comment("SE cost for anvil operations")
                    .define("se_cost", 0.1);

            enchantmentCost = builder
                    .comment("Enchantment cost for anvil operations")
                    .define("enchantment_cost", 0.1);

            builder.pop();

            // Attack configuration
            builder.comment("Attack related configuration")
                    .push("attack");

            refineAttackBonus = builder
                    .comment("Attack bonus per refine level")
                    .define("refine_attack_bonus", 0.002);

            rankMaxAttackBonus = builder
                    .comment("Maximum attack bonus from rank")
                    .define("rank_max_attack_bonus", 0.2);

            thousandKillAttackBonus = builder
                    .comment("Attack bonus for thousand kills")
                    .define("thousand_kill_attack_bonus", 0.1);

            tenThousandKillAttackBonus = builder
                    .comment("Attack bonus for ten thousand kills")
                    .define("ten_thousand_kill_attack_bonus", 0.1);

            thousandRefineAttackBonus = builder
                    .comment("Attack bonus for thousand refines")
                    .define("thousand_refine_attack_bonus", 0.1);

            tenThousandRefineAttackBonus = builder
                    .comment("Attack bonus for ten thousand refines")
                    .define("ten_thousand_refine_attack_bonus", 0.1);

            durabilityLoss = builder
                    .comment("Durability loss per slash attack")
                    .define("durability_loss", 1.0);

            durabilityReductionRate = builder
                    .comment("Define the effect of durability enchantment and durability reduction")
                    .define("durability_reduction_rate", 0.5);

            builder.pop();

            // Copy SA configuration
            builder.comment("Copy Special Attack configuration")
                    .push("copy_sa");

            getSuccessRate = builder
                    .comment("Success rate for getting special attacks")
                    .define("get_success_rate", 0.33);

            lossRefine = builder
                    .comment("Refine loss on failure")
                    .define("loss_refine", 0.2);

            minRefine = builder
                    .comment("Minimum refine required")
                    .define("min_refine", 500.0);

            minKill = builder
                    .comment("Minimum kills required")
                    .define("min_kill", 1000.0);

            builder.pop();

            // Dropped item configuration
            builder.comment("Dropped item configuration")
                    .push("dropped_items");

            soulDropChance = builder
                    .comment("Soul drop chance")
                    .define("soul_drop_chance", 0.1);

            tinySoulDropChance = builder
                    .comment("Tiny soul drop chance")
                    .define("tiny_soul_drop_chance", 0.2);

            enchantmentSoulDropChance = builder
                    .comment("Enchantment soul drop chance")
                    .define("enchantment_soul_drop_chance", 0.05);

            enchantmentSoulSuccessRate = builder
                    .comment("Enchantment soul success rate")
                    .define("enchantment_soul_success_rate", 0.25);

            entityDropChance = builder
                    .comment("Entity drop chance")
                    .define("entity_drop_chance", 0.01);

            builder.pop();

            // Slash effect configuration
            builder.comment("Slash effect configuration")
                    .push("slash_effect");

            useBlockParticle = builder
                    .comment("Whether to use block particles")
                    .define("use_block_particle", true);

            builder.pop();

            // Slayer style arts configuration
            builder.comment("Slayer style arts configuration")
                    .push("slayer_style");

            sprintAttack = builder
                    .comment("Sprint attack bonus")
                    .define("sprint_attack", 0.01);

            sprintPower = builder
                    .comment("Sprint power")
                    .define("sprint_power", 5.0);

            builder.pop();

            // Summoned sword configuration
            builder.comment("Summoned sword configuration")
                    .push("summoned_sword");

            ordinaryAttack = builder
                    .comment("Ordinary attack damage")
                    .define("ordinary_attack", 0.15);

            blisteringAttack = builder
                    .comment("Blistering attack damage")
                    .define("blistering_attack", 0.1);

            blisteringAttackNumber = builder
                    .comment("Number of blistering attacks")
                    .define("blistering_attack_number", 8);

            heavyRainAttack = builder
                    .comment("Heavy rain attack damage")
                    .define("heavy_rain_attack", 0.12);

            heavyRainAttackNumber = builder
                    .comment("Number of heavy rain attacks")
                    .define("heavy_rain_attack_number", 16);

            heavyRainYOffset = builder
                    .comment("Y offset for heavy rain attacks")
                    .define("heavy_rain_y_offset", 10.0);

            heavyRainOffsetX = builder
                    .comment("X offset for heavy rain area")
                    .define("heavy_rain_offset_x", 6.0);

            heavyRainOffsetY = builder
                    .comment("Y offset for heavy rain area")
                    .define("heavy_rain_offset_y", 3.0);

            heavyRainOffsetZ = builder
                    .comment("Z offset for heavy rain area")
                    .define("heavy_rain_offset_z", 6.0);

            stormSwordAttack = builder
                    .comment("Storm sword attack damage")
                    .define("storm_sword_attack", 0.1);

            stormSwordAttackNumber = builder
                    .comment("Number of storm sword attacks")
                    .define("storm_sword_attack_number", 8);

            spiralSwordAttack = builder
                    .comment("Spiral sword attack damage")
                    .define("spiral_sword_attack", 0.15);

            spiralSwordAttackNumber = builder
                    .comment("Number of spiral sword attacks")
                    .define("spiral_sword_attack_number", 8);

            builder.pop();

            // UP SE configuration
            builder.comment("UP SE configuration")
                    .push("up_se");

            probabilityFactor = builder
                    .comment("Probability factor for UP SE")
                    .define("probability_factor", 0.1);

            builder.pop();

            // Enchantment configuration
            builder.comment("Enchantment configuration")
                    .push("enchantment");

            sharpnessAttackBonus = builder
                    .comment("Attack bonus per sharpness level")
                    .define("sharpness_attack_bonus", 0.03);

            smiteAttackBonus = builder
                    .comment("Attack bonus per smite level against undead")
                    .define("smite_attack_bonus", 0.05);

            baneOfArthropodsAttackBonus = builder
                    .comment("Attack bonus per bane of arthropods level against arthropods")
                    .define("bane_of_arthropods_attack_bonus", 0.05);

            powerAttackBonus = builder
                    .comment("Attack bonus per power level for summoned sword attacks")
                    .define("power_attack_bonus", 0.05);

            sweepingEdgeSizeBonus = builder
                    .comment("Attack range per sweeping edge level")
                    .define("sweeping_edge_size_bonus", 0.04);

            builder.pop();

            // Preload configuration
            builder.comment("Preload configuration")
                    .push("preload");

            preload = builder
                    .comment("Whether to enable preloading")
                    .define("preload", true);

            builder.pop();
        }
    }
}
