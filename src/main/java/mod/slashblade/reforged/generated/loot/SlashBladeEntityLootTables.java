package mod.slashblade.reforged.generated.loot;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.content.loot.LootItemStack;
import mod.slashblade.reforged.generated.group.SlashBladeItemStacks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * SlashBlade 实体战利品表生成器
 * 专门处理特定实体（如凋零、末影龙）的额外掉落物品
 */
public class SlashBladeEntityLootTables extends EntityLootSubProvider {

    public SlashBladeEntityLootTables(HolderLookup.Provider lookup) {
        super(FeatureFlags.REGISTRY.allFlags(), lookup);
    }

    @Override
    public void generate() {
        // 为凋零生成额外战利品表
        this.add(
                EntityType.WITHER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1))
                                        .add(
                                                LootItemStack.lootTableItem(SlashBladeItemStacks.SANGE_BROKEN.get())
                                        )
                        )

        );

        // 为末影龙生成额外战利品表  
        this.add(
                EntityType.ENDER_DRAGON,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1))
                                        .add(
                                                LootItemStack.lootTableItem(SlashBladeItemStacks.YAMATO_BROKEN.get())
                                        )
                        )
        );
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        // 返回我们要处理的实体类型
        return Stream.of(
                EntityType.WITHER,
                EntityType.ENDER_DRAGON
        );
    }



}
