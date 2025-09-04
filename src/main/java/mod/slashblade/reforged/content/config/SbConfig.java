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
        public final ModConfigSpec.ConfigValue<Double> thousandKillReward;
        public final ModConfigSpec.ConfigValue<Double> tenThousandKillReward;
        public final ModConfigSpec.ConfigValue<Double> thousandRefineReward;
        public final ModConfigSpec.ConfigValue<Double> tenThousandRefineReward;

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
                    .defineInRange("se_cost", 0.1, 0.0, 1.0);

            enchantmentCost = builder
                    .comment("Enchantment cost for anvil operations")
                    .defineInRange("enchantment_cost", 0.1, 0.0, 1.0);

            builder.pop();

            // Attack configuration
            builder.comment("Attack related configuration")
                    .push("attack");

            refineAttackBonus = builder
                    .comment("Attack bonus per refine level")
                    .defineInRange("refine_attack_bonus", 0.002, 0.0, 0.1);

            rankMaxAttackBonus = builder
                    .comment("Maximum attack bonus from rank")
                    .defineInRange("rank_max_attack_bonus", 0.2, 0.0, 1.0);

            thousandKillReward = builder
                    .comment("Reward for thousand kills")
                    .defineInRange("thousand_kill_reward", 0.1, 0.0, 1.0);

            tenThousandKillReward = builder
                    .comment("Reward for ten thousand kills")
                    .defineInRange("ten_thousand_kill_reward", 0.1, 0.0, 1.0);

            thousandRefineReward = builder
                    .comment("Reward for thousand refines")
                    .defineInRange("thousand_refine_reward", 0.1, 0.0, 1.0);

            tenThousandRefineReward = builder
                    .comment("Reward for ten thousand refines")
                    .defineInRange("ten_thousand_refine_reward", 0.1, 0.0, 1.0);

            builder.pop();

            // Copy SA configuration
            builder.comment("Copy Special Attack configuration")
                    .push("copy_sa");

            getSuccessRate = builder
                    .comment("Success rate for getting special attacks")
                    .defineInRange("get_success_rate", 0.33, 0.0, 1.0);

            lossRefine = builder
                    .comment("Refine loss on failure")
                    .defineInRange("loss_refine", 0.2, 0.0, 1.0);

            minRefine = builder
                    .comment("Minimum refine required")
                    .defineInRange("min_refine", 500.0, 0.0, 10000.0);

            minKill = builder
                    .comment("Minimum kills required")
                    .defineInRange("min_kill", 1000.0, 0.0, 100000.0);

            builder.pop();

            // Dropped item configuration
            builder.comment("Dropped item configuration")
                    .push("dropped_items");

            soulDropChance = builder
                    .comment("Soul drop chance")
                    .defineInRange("soul_drop_chance", 0.1, 0.0, 1.0);

            tinySoulDropChance = builder
                    .comment("Tiny soul drop chance")
                    .defineInRange("tiny_soul_drop_chance", 0.2, 0.0, 1.0);

            enchantmentSoulDropChance = builder
                    .comment("Enchantment soul drop chance")
                    .defineInRange("enchantment_soul_drop_chance", 0.05, 0.0, 1.0);

            enchantmentSoulSuccessRate = builder
                    .comment("Enchantment soul success rate")
                    .defineInRange("enchantment_soul_success_rate", 0.25, 0.0, 1.0);

            entityDropChance = builder
                    .comment("Entity drop chance")
                    .defineInRange("entity_drop_chance", 0.01, 0.0, 1.0);

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
                    .defineInRange("sprint_attack", 0.01, 0.0, 1.0);

            sprintPower = builder
                    .comment("Sprint power")
                    .defineInRange("sprint_power", 5.0, 0.0, 100.0);

            builder.pop();

            // Summoned sword configuration
            builder.comment("Summoned sword configuration")
                    .push("summoned_sword");

            ordinaryAttack = builder
                    .comment("Ordinary attack damage")
                    .defineInRange("ordinary_attack", 0.15, 0.0, 10.0);

            blisteringAttack = builder
                    .comment("Blistering attack damage")
                    .defineInRange("blistering_attack", 0.1, 0.0, 10.0);

            blisteringAttackNumber = builder
                    .comment("Number of blistering attacks")
                    .defineInRange("blistering_attack_number", 8, 1, 64);

            heavyRainAttack = builder
                    .comment("Heavy rain attack damage")
                    .defineInRange("heavy_rain_attack", 0.12, 0.0, 10.0);

            heavyRainAttackNumber = builder
                    .comment("Number of heavy rain attacks")
                    .defineInRange("heavy_rain_attack_number", 16, 1, 64);

            heavyRainYOffset = builder
                    .comment("Y offset for heavy rain attacks")
                    .defineInRange("heavy_rain_y_offset", 10.0, 0.0, 100.0);

            heavyRainOffsetX = builder
                    .comment("X offset for heavy rain area")
                    .defineInRange("heavy_rain_offset_x", 6.0, 0.0, 100.0);

            heavyRainOffsetY = builder
                    .comment("Y offset for heavy rain area")
                    .defineInRange("heavy_rain_offset_y", 3.0, 0.0, 100.0);

            heavyRainOffsetZ = builder
                    .comment("Z offset for heavy rain area")
                    .defineInRange("heavy_rain_offset_z", 6.0, 0.0, 100.0);

            stormSwordAttack = builder
                    .comment("Storm sword attack damage")
                    .defineInRange("storm_sword_attack", 0.1, 0.0, 10.0);

            stormSwordAttackNumber = builder
                    .comment("Number of storm sword attacks")
                    .defineInRange("storm_sword_attack_number", 8, 1, 64);

            spiralSwordAttack = builder
                    .comment("Spiral sword attack damage")
                    .defineInRange("spiral_sword_attack", 0.15, 0.0, 10.0);

            spiralSwordAttackNumber = builder
                    .comment("Number of spiral sword attacks")
                    .defineInRange("spiral_sword_attack_number", 8, 1, 64);

            builder.pop();

            // UP SE configuration
            builder.comment("UP SE configuration")
                    .push("up_se");

            probabilityFactor = builder
                    .comment("Probability factor for UP SE")
                    .defineInRange("probability_factor", 0.1, 0.0, 1.0);

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
