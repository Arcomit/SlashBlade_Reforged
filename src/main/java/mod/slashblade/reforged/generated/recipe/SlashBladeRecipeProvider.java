package mod.slashblade.reforged.generated.recipe;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.init.SbRecipeSerializer;
import mod.slashblade.reforged.utils.tuple.Tuple2;
import mod.slashblade.reforged.utils.tuple.Tuple3;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SlashBladeRecipeProvider extends RecipeProvider {
    String namespace;
    List<Class<?>> classes;

    public SlashBladeRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, List<Class<?>> classes, String namespace) {
        super(output, registries);
        this.classes = classes;
        this.namespace = namespace;
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        classes.stream()
                .flatMap(c -> Arrays.stream(c.getDeclaredFields()))
                .filter(field -> Supplier.class.isAssignableFrom(field.getType()))
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> Modifier.isFinal(field.getModifiers()))
                .peek(field -> field.setAccessible(true))
                .map(
                        field -> {
                            try {
                                return new Tuple2<>(field, field.get(null));
                            } catch (IllegalAccessException e) {
                                SlashbladeMod.LOGGER.warn("", e); // TODO 日志输出
                                return null;
                            }
                        }
                )
                .filter(Objects::nonNull)
                .filter(o -> o.getB() instanceof Supplier<?>)
                .map(o -> new Tuple2<>(o.getA(), ((Supplier<?>) o.getB()).get()))
                .filter(o -> o.getB() instanceof Recipe)
                .map(
                        o -> new Tuple2<>(
                                ResourceLocation.fromNamespaceAndPath(namespace, o.getA().getName().toLowerCase()),
                                (Recipe<?>) o.getB()))
                .map(
                        t -> new Tuple3<>(
                                t.getA(),
                                t.getB(),
                                output.advancement()
                                        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(t.getA()))
                                        .rewards(AdvancementRewards.Builder.recipe(t.getA()))
                                        .requirements(AdvancementRequirements.Strategy.OR)
                        )
                )
                .forEach(t -> output.accept(
                                t.getA(),
                                t.getB(),
                                t.getC().build(
                                        t.getA().withPrefix("recipes/" + RecipeCategory.COMBAT.getFolderName() + "/")
                                )
                        )
                );


    }


}
