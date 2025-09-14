package mod.slashblade.reforged.generated.recipe;

import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.content.init.SbRecipeSerializer;
import mod.slashblade.reforged.content.recipe.IRecipeInputItem;
import mod.slashblade.reforged.content.recipe.SlashBladeRecipe;
import mod.slashblade.reforged.generated.group.ItemStackBuilder;
import mod.slashblade.reforged.generated.group.SlashBladeItemStacks;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SlashBladeRecipes {
    public static final Supplier<SlashBladeRecipe> ANONYMITY_WOOD_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "  A",
                            " A ",
                            "S  "
                    ),
                    Map.of(
                            'A', new IRecipeInputItem.IngredientRecipeInputItem(ItemTags.LOGS),
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(
                                    new ItemStackBuilder(new ItemStack(Items.WOODEN_SWORD))
                                            .setDamageValue(1)
                                            .build()
                            )
                    ),
                    '~',
                    SlashBladeItemStacks.ANONYMITY_WOOD.get()
            );

    public static final Supplier<SlashBladeRecipe> SHARPNESS_WHITE_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "  A",
                            " A ",
                            "#B "
                    ),
                    Map.of(
                            'A', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.INGOTS_IRON),
                            'B', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.INGOTS_GOLD),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(SlashBladeItemStacks.ANONYMITY_WOOD.get())
                    ),
                    '#',
                    SlashBladeItemStacks.SHARPNESS_WHITE.get()
            );

    public static final Supplier<SlashBladeRecipe> ANONYMITY_BAMBOO_LIGHT = () ->
            new SlashBladeRecipe(
                    List.of(
                            "  A",
                            " A ",
                            "#  "
                    ),
                    Map.of(
                            'A', new IRecipeInputItem.IngredientRecipeInputItem(ItemTags.PANDA_FOOD),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(SlashBladeItemStacks.SHARPNESS_WHITE.get())
                    ),
                    '#',
                    SlashBladeItemStacks.ANONYMITY_BAMBOO_LIGHT.get()
            );

    public static final Supplier<SlashBladeRecipe> NOTED_SILVER_BAMBOO_LIGHT = () ->
            new SlashBladeRecipe(
                    List.of(
                            " EI",
                            "S#B",
                            "PS "
                    ),
                    Map.of(
                            'E', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.EGGS),
                            'I', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.INGOTS_IRON),
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STRINGS),
                            'B', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.DYES_BLACK),
                            'P', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(Items.PAPER)),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(SlashBladeItemStacks.ANONYMITY_BAMBOO_LIGHT.get())
                    ),
                    '#',
                    SlashBladeItemStacks.NOTED_SILVER_BAMBOO_LIGHT.get()
            );

    public static final Supplier<SlashBladeRecipe> IRONWOOD_TAGAYASAN_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "SES",
                            "P#P",
                            "SES"
                    ),
                    Map.of(
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(SbItems.PROUD_SOUL_SPHERE.get())),
                            'E', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(Items.ENDER_EYE)),
                            'P', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.ENDER_PEARLS),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                                    new ItemStackBuilder(SlashBladeItemStacks.ANONYMITY_WOOD.get())
                                            .set(
                                                    SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                                    slashBladeLogic -> slashBladeLogic.toBuilder()
                                                            .kill(1000)
                                                            .build(),
                                                    SlashBladeLogic::new
                                            )
                                            .build()
                            )
                    ),
                    '#',
                    new ItemStackBuilder(SlashBladeItemStacks.IRONWOOD_TAGAYASAN.get())
                            .setEnchantment(
                                    Map.of(
                                            Enchantments.SMITE, 3,
                                            Enchantments.UNBREAKING, 3
                                    )
                            )
                            .build()
            );

    public static final Supplier<SlashBladeRecipe> SLASH_BLADE_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            " BI",
                            "L#C",
                            "SG "
                    ),
                    Map.of(
                            'L', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_LAPIS),
                            'C', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_COAL),
                            'I', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(SbItems.PROUD_SOUL_SPHERE.get())),
                            'B', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.RODS_BLAZE),
                            'G', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.INGOTS_GOLD),
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STRINGS),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(SlashBladeItemStacks.SHARPNESS_WHITE.get())
                    ),
                    '#',
                    SlashBladeItemStacks.SLASH_BLADE.get()
            );

    public static final Supplier<SlashBladeRecipe> NOBLE_TUKUMO_VIOLET_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "ESD",
                            "R#L",
                            "ISG"
                    ),
                    Map.of(
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_SPHERE.get()),
                            'E', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_EMERALD),
                            'D', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_DIAMOND),
                            'R', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_REDSTONE),
                            'L', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_LAPIS),
                            'I', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_IRON),
                            'G', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STORAGE_BLOCKS_GOLD),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                                    new ItemStackBuilder(SlashBladeItemStacks.SLASH_BLADE.get())
                                            .set(
                                                    SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                                    slashBladeLogic ->
                                                            slashBladeLogic.toBuilder()
                                                                    .kill(1000)
                                                                    .build(),
                                                    SlashBladeLogic::new
                                            )
                                            .setEnchantment(
                                                    Map.of(
                                                            Enchantments.FIRE_ASPECT, 1
                                                    )
                                            )
                                            .build()
                            )
                    ),
                    '#',
                    new ItemStackBuilder(SlashBladeItemStacks.NOBLE_TUKUMO_VIOLET.get())
                            .setEnchantment(
                                    Map.of(
                                            Enchantments.SHARPNESS, 4,
                                            Enchantments.UNBREAKING, 4,
                                            Enchantments.FIRE_ASPECT, 1
                                    )
                            )
                            .build()
            );

    public static final Supplier<SlashBladeRecipe> SLASH_BLADE_RECIPE_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "SSS",
                            "S#S",
                            "SSS"
                    ),
                    Map.of(
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_SPHERE.get()),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                                    new ItemStackBuilder(SlashBladeItemStacks.SLASH_BLADE.get())
                                            .set(
                                                    SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                                    slashBladeLogic ->
                                                            slashBladeLogic.toBuilder()
                                                                    .refine(20)
                                                                    .proudSoul(10000)
                                                                    .build(),
                                                    SlashBladeLogic::new
                                            )
                                            .setEnchantment(
                                                    Map.of(
                                                            Enchantments.FIRE_ASPECT, 1
                                                    )
                                            )
                                            .build()
                            )
                    ),
                    '#',
                    SlashBladeItemStacks.CHIZURU_MURAMASA.get()
            );

    public static final Supplier<SlashBladeRecipe> SHARPNESS_NAMELESS_RUBY_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "RIP",
                            "I# ",
                            "S  "
                    ),
                    Map.of(
                            'R', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.DYES_RED),
                            'P', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL.get()),
                            'I', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_INGOT.get()),
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(new ItemStack(Items.IRON_SWORD)),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(SlashBladeItemStacks.SCABBARD.get())
                    ),
                    '#',
                    SlashBladeItemStacks.SHARPNESS_NAMELESS_RUBY.get()
            );

    public static final Supplier<SlashBladeRecipe> CRESCENT_BLACK_FOXES_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "SPS",
                            "S#S",
                            "SWS"
                    ),
                    Map.of(
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL.get()),
                            'P', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()),
                            'W', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.CROPS_WHEAT),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                                    new ItemStackBuilder(SlashBladeItemStacks.SHARPNESS_NAMELESS_RUBY.get())
                                            .set(
                                                    SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                                    slashBladeLogic ->
                                                            slashBladeLogic.toBuilder()
                                                                    .kill(199)
                                                                    .refine(1)
                                                                    .proudSoul(1000)
                                                                    .build(),
                                                    SlashBladeLogic::new
                                            )
                                            .setEnchantment(
                                                    Map.of(
                                                            Enchantments.SMITE, 1
                                                    )
                                            )
                                            .build()
                            )
                    ),
                    '#',
                    new ItemStackBuilder(SlashBladeItemStacks.CRESCENT_BLACK_FOXES.get())
                            .setEnchantment(
                                    Map.of(
                                            Enchantments.SMITE, 4,
                                            Enchantments.KNOCKBACK, 2,
                                            Enchantments.FIRE_ASPECT, 2
                                    )
                            )
                            .build()
            );

    public static final Supplier<SlashBladeRecipe> CRESCENT_WEISS_FOXES_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "SPS",
                            "S#S",
                            "SWS"
                    ),
                    Map.of(
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL.get()),
                            'P', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()),
                            'W', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.CROPS_WHEAT),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                                    new ItemStackBuilder(SlashBladeItemStacks.SHARPNESS_NAMELESS_RUBY.get())
                                            .set(
                                                    SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                                    slashBladeLogic ->
                                                            slashBladeLogic.toBuilder()
                                                                    .kill(199)
                                                                    .refine(1)
                                                                    .proudSoul(1000)
                                                                    .build(),
                                                    SlashBladeLogic::new
                                            )
                                            .setEnchantment(
                                                    Map.of(
                                                            Enchantments.LOOTING, 1
                                                    )
                                            )
                                            .build()
                            )
                    ),
                    '#',
                    new ItemStackBuilder(SlashBladeItemStacks.CRESCENT_WEISS_FOX.get())
                            .setEnchantment(
                                    Map.of(
                                            Enchantments.KNOCKBACK, 2,
                                            Enchantments.FIRE_ASPECT, 2,
                                            Enchantments.BANE_OF_ARTHROPODS, 2,
                                            Enchantments.UNBREAKING, 3,
                                            Enchantments.LOOTING, 3
                                    )
                            )
                            .build()
            );

    public static final Supplier<SlashBladeRecipe> WOODEN_RODAI_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "  T",
                            " # ",
                            "WS "
                    ),
                    Map.of(
                            'T', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()),
                            'S', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STRINGS),
                            'W', new IRecipeInputItem.IngredientRecipeInputItem(Items.WOODEN_SWORD),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                                    new ItemStackBuilder(SlashBladeItemStacks.NOTED_SILVER_BAMBOO_LIGHT.get())
                                            .set(
                                                    SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                                    slashBladeLogic -> slashBladeLogic.toBuilder()
                                                            .kill(100)
                                                            .build(),
                                                    SlashBladeLogic::new
                                            )
                                            .build()
                            )
                    ),
                    '#',
                    SlashBladeItemStacks.WOODEN_RODAI.get()
            );

    public static final Supplier<SlashBladeRecipe> STONE_RODAI_RECIPE = () -> new SlashBladeRecipe(
            List.of(
                    "  T",
                    " # ",
                    "WS "
            ),
            Map.of(
                    'T', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()),
                    'S', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STRINGS),
                    'W', new IRecipeInputItem.IngredientRecipeInputItem(Items.STONE_SWORD),
                    '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                            new ItemStackBuilder(SlashBladeItemStacks.WOODEN_RODAI.get())
                                    .set(
                                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                            slashBladeLogic -> slashBladeLogic.toBuilder()
                                                    .kill(500)
                                                    .build(),
                                            SlashBladeLogic::new
                                    )
                                    .build()
                    )
            ),
            '#',
            SlashBladeItemStacks.STONE_RODAI.get()
    );

    public static final Supplier<SlashBladeRecipe> NAMED_STEEL_RODAI_RECIPE = () -> new SlashBladeRecipe(
            List.of(
                    "  T",
                    " # ",
                    "WS "
            ),
            Map.of(
                    'T', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()),
                    'S', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STRINGS),
                    'W', new IRecipeInputItem.IngredientRecipeInputItem(Items.IRON_SWORD),
                    '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                            new ItemStackBuilder(SlashBladeItemStacks.STONE_RODAI.get())
                                    .set(
                                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                            slashBladeLogic -> slashBladeLogic.toBuilder()
                                                    .kill(1500)
                                                    .refine(20)
                                                    .build(),
                                            SlashBladeLogic::new
                                    )
                                    .build()
                    )
            ),
            '#',
            SlashBladeItemStacks.NAMED_STEEL_RODAI.get()
    );

    public static final Supplier<SlashBladeRecipe> NAMED_GOLDEN_RODAI_RECIPE = () -> new SlashBladeRecipe(
            List.of(
                    "  T",
                    " # ",
                    "WS "
            ),
            Map.of(
                    'T', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()),
                    'S', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STRINGS),
                    'W', new IRecipeInputItem.IngredientRecipeInputItem(Items.GOLDEN_SWORD),
                    '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                            new ItemStackBuilder(SlashBladeItemStacks.NAMED_STEEL_RODAI.get())
                                    .set(
                                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                            slashBladeLogic -> slashBladeLogic.toBuilder()
                                                    .kill(2000)
                                                    .refine(50)
                                                    .build(),
                                            SlashBladeLogic::new
                                    )
                                    .build()
                    )
            ),
            '#',
            SlashBladeItemStacks.NAMED_GOLDEN_RODAI.get()
    );

    public static final Supplier<SlashBladeRecipe> NAMED_DIAMOND_RODAI_RECIPE = () -> new SlashBladeRecipe(
            List.of(
                    "  T",
                    " # ",
                    "WS "
            ),
            Map.of(
                    'T', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()),
                    'S', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STRINGS),
                    'W', new IRecipeInputItem.IngredientRecipeInputItem(Items.DIAMOND_SWORD),
                    '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                            new ItemStackBuilder(SlashBladeItemStacks.NAMED_GOLDEN_RODAI.get())
                                    .set(
                                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                            slashBladeLogic -> slashBladeLogic.toBuilder()
                                                    .kill(3000)
                                                    .refine(100)
                                                    .build(),
                                            SlashBladeLogic::new
                                    )
                                    .build()
                    )
            ),
            '#',
            SlashBladeItemStacks.NAMED_DIAMOND_RODAI.get()
    );

    public static final Supplier<SlashBladeRecipe> NAMED_NETHERITE_RODAI_RECIPE = () -> new SlashBladeRecipe(
            List.of(
                    "  T",
                    " # ",
                    "WS "
            ),
            Map.of(
                    'T', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()),
                    'S', new IRecipeInputItem.IngredientRecipeInputItem(Tags.Items.STRINGS),
                    'W', new IRecipeInputItem.IngredientRecipeInputItem(Items.NETHERITE_SWORD),
                    '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                            new ItemStackBuilder(SlashBladeItemStacks.NAMED_DIAMOND_RODAI.get())
                                    .set(
                                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                            slashBladeLogic -> slashBladeLogic.toBuilder()
                                                    .kill(5000)
                                                    .refine(200)
                                                    .build(),
                                            SlashBladeLogic::new
                                    )
                                    .build()
                    )
            ),
            '#',
            SlashBladeItemStacks.NAMED_NETHERITE_RODAI.get()
    );

    public static final Supplier<SlashBladeRecipe> REPAIR_SABIGATANA_RECIPE = () ->
            new SlashBladeRecipe(
                    List.of(
                            "  I",
                            " I ",
                            "#  "
                    ),
                    Map.of(
                            'I', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_INGOT.get()),
                            '#', new IRecipeInputItem.SlashBladeRecipeInputItem(SlashBladeItemStacks.SABIGATANA_BROKEN.get())
                    ),
                    '#',
                    SlashBladeItemStacks.SABIGATANA.get()
            );

    public static final Supplier<SlashBladeRecipe> STEEL_DOUTANUKI_RECIPE = () -> new SlashBladeRecipe(
            List.of(
                    "  S",
                    " # ",
                    "S  "
            ),
            Map.of(
                    'S', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_SPHERE.get()),
                    '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                            new ItemStackBuilder(SlashBladeItemStacks.SABIGATANA.get())
                                    .set(
                                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                            slashBladeLogic -> slashBladeLogic.toBuilder()
                                                    .kill(100)
                                                    .proudSoul(1000)
                                                    .build(),
                                            SlashBladeLogic::new
                                    )
                                    .build()
                    )
            ),
            '#',
            SlashBladeItemStacks.STEEL_DOUTANUKI.get()
    );

    public static final Supplier<SlashBladeRecipe> SANGE_RECIPE = () -> new SlashBladeRecipe(
            List.of(
                    "SSS",
                    "S#S",
                    "SSS"
            ),
            Map.of(
                    'S', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_CRYSTAL.get()),
                    '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                            new ItemStackBuilder(SlashBladeItemStacks.SANGE_BROKEN.get())
                                    .set(
                                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                            slashBladeLogic -> slashBladeLogic.toBuilder()
                                                    .refine(100)
                                                    .build(),
                                            SlashBladeLogic::new
                                    )
                                    .build()
                    )
            ),
            '#',
            SlashBladeItemStacks.SANGE.get()
    );

    public static final Supplier<SlashBladeRecipe> REPAIR_YAMATO_RECIPE = () -> new SlashBladeRecipe(
            List.of(
                    "SSS",
                    "S#S",
                    "SSS"
            ),
            Map.of(
                    'S', new IRecipeInputItem.IngredientRecipeInputItem(SbItems.PROUD_SOUL_SPHERE.get()),
                    '#', new IRecipeInputItem.SlashBladeRecipeInputItem(
                            new ItemStackBuilder(SlashBladeItemStacks.YAMATO_BROKEN.get())
                                    .set(
                                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                                            slashBladeLogic -> slashBladeLogic.toBuilder()
                                                    .refine(100)
                                                    .build(),
                                            SlashBladeLogic::new
                                    )
                                    .build()
                    )
            ),
            '#',
            SlashBladeItemStacks.YAMATO.get()
    );

    public static final Supplier<ShapelessRecipe> PROUD_SOUL_RECIPE = () -> new ShapelessRecipe(
            SbItems.PROUD_SOUL.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            new ItemStack(SbItems.PROUD_SOUL.get()),
            NonNullList.of(Ingredient.EMPTY, Ingredient.of(SbItems.PROUD_SOUL_TINY.get()), Ingredient.of(SbItems.PROUD_SOUL_TINY.get()))
    );

    public static final Supplier<ShapelessRecipe> PROUD_SOUL_TINY_RECIPE = () -> new ShapelessRecipe(
            SbItems.PROUD_SOUL_TINY.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            new ItemStack(SbItems.PROUD_SOUL_TINY.get(), 2),
            NonNullList.of(Ingredient.EMPTY, Ingredient.of(SbItems.PROUD_SOUL.get()))
    );

    public static final Supplier<ShapedRecipe> PROUD_SOUL_INGOT_RECIPE = () -> new ShapedRecipe(
            SbItems.PROUD_SOUL_INGOT.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            ShapedRecipePattern.of(
                    Map.of(
                            'P', Ingredient.of(SbItems.PROUD_SOUL.get()),
                            'I', Ingredient.of(Items.IRON_INGOT)
                    ),
                    List.of(
                            " P ",
                            "PIP",
                            " P "
                    )
            ),
            new ItemStack(SbItems.PROUD_SOUL_INGOT.get())
    );

    public static final Supplier<SmeltingRecipe> PROUD_SOUL_SPHERE_RECIPE = () -> new SmeltingRecipe(
            SbItems.PROUD_SOUL_SPHERE.get().getDescriptionId(),
            CookingBookCategory.MISC,
            Ingredient.of(SbItems.PROUD_SOUL_INGOT.get()),
            new ItemStack(SbItems.PROUD_SOUL_SPHERE.get()),
            3F,
            10000
    );

    public static final Supplier<ShapedRecipe> PROUD_SOUL_CRYSTAL_RECIPE = () -> new ShapedRecipe(
            SbItems.PROUD_SOUL_CRYSTAL.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            ShapedRecipePattern.of(
                    Map.of(
                            'S', Ingredient.of(SbItems.PROUD_SOUL_SPHERE.get()),
                            '#', Ingredient.of(Items.NETHER_STAR)
                    ),
                    List.of(
                            "SSS",
                            "S#S",
                            "SSS"
                    )
            ),
            new ItemStack(SbItems.PROUD_SOUL_CRYSTAL.get(), 8)
    );

    /*public static final Supplier<ShapedRecipe> PROUD_SOUL_TRAPEZOHEDRON_RECIPE = () -> new ShapedRecipe(

    );*/

    public static final Supplier<ShapelessRecipe> BLADESTAND_1_RECIPE = () -> new ShapelessRecipe(
            SbItems.BLADESTAND_1.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            new ItemStack(SbItems.BLADESTAND_1.get()),
            NonNullList.of(Ingredient.EMPTY, Ingredient.of(ItemTags.WOODEN_FENCES), Ingredient.of(SbItems.PROUD_SOUL.get()))
    );

    public static final Supplier<ShapelessRecipe> BLADESTAND_2_RECIPE = () -> new ShapelessRecipe(
            SbItems.BLADESTAND_2.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            new ItemStack(SbItems.BLADESTAND_2.get()),
            NonNullList.of(Ingredient.EMPTY, Ingredient.of(ItemTags.WOODEN_FENCES), Ingredient.of(SbItems.PROUD_SOUL_INGOT.get()))
    );

    public static final Supplier<ShapelessRecipe> BLADESTAND_V_RECIPE = () -> new ShapelessRecipe(
            SbItems.BLADESTAND_V.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            new ItemStack(SbItems.BLADESTAND_V.get()),
            NonNullList.of(Ingredient.EMPTY, Ingredient.of(ItemTags.WOODEN_FENCES), Ingredient.of(SbItems.PROUD_SOUL_TINY.get()))
    );

    public static final Supplier<ShapelessRecipe> BLADESTAND_S_RECIPE = () -> new ShapelessRecipe(
            SbItems.BLADESTAND_S.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            new ItemStack(SbItems.BLADESTAND_S.get()),
            NonNullList.of(Ingredient.EMPTY, Ingredient.of(ItemTags.WOODEN_FENCES), Ingredient.of(SbItems.PROUD_SOUL_SPHERE.get()))
    );

    public static final Supplier<ShapelessRecipe> BLADESTAND_1W_RECIPE = () -> new ShapelessRecipe(
            SbItems.BLADESTAND_1W.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            new ItemStack(SbItems.BLADESTAND_1W.get()),
            NonNullList.of(Ingredient.EMPTY, Ingredient.of(ItemTags.WOODEN_FENCES), Ingredient.of(SbItems.PROUD_SOUL_CRYSTAL.get()))
    );

    public static final Supplier<ShapelessRecipe> BLADESTAND_2W_RECIPE = () -> new ShapelessRecipe(
            SbItems.BLADESTAND_2W.get().getDescriptionId(),
            CraftingBookCategory.MISC,
            new ItemStack(SbItems.BLADESTAND_2W.get()),
            NonNullList.of(Ingredient.EMPTY, Ingredient.of(ItemTags.WOODEN_FENCES), Ingredient.of(SbItems.PROUD_SOUL_TRAPEZOHEDRON.get()))
    );
}
