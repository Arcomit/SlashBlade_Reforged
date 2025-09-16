package mod.slashblade.reforged.utils.helper;

import lombok.Getter;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SlashBladeHelper {

    public static boolean meetConditions(@NotNull ItemStack input, @NotNull SlashBladeLogic inputLogic, @NotNull ItemStack model, @NotNull SlashBladeLogic modelLogic) {

        if (!inputLogic.meetConditions(modelLogic)) {
            return false;
        }

        if (!meetEnchantments(input, model)) {
            return false;
        }

        return true;
    }

    public static boolean meetEnchantments(@NotNull ItemStack input, @NotNull ItemStack model) {
        return meetEnchantments(input.getTagEnchantments(), model.getTagEnchantments());

    }

    public static boolean meetEnchantments(@NotNull ItemEnchantments input, @NotNull ItemEnchantments model) {

        for(Holder<Enchantment> enchantmentHolder : model.keySet()) {
            int inputLevel = input.getLevel(enchantmentHolder);
            int modelLevel = model.getLevel(enchantmentHolder);

            if (inputLevel < modelLevel) {
                return false;
            }
        }

        return true;
    }

    @NotNull
    public static ItemStack transformation(@NotNull ItemStack input, @NotNull SlashBladeLogic inputLogic, @NotNull ItemStack target, @NotNull SlashBladeLogic targetLogic) {

        ItemStack out = target.copy();

        SlashBladeLogic outLogic = out.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);

        if (outLogic == null) {
            return out;
        }

        outLogic = outLogic.toBuilder()
                .proudSoul(inputLogic.getProudSoul())
                .refine(inputLogic.getRefine())
                .kill(inputLogic.getKill())
                .key(targetLogic.getKey())
                .build(); // TODO 配置


        out.set(SbDataComponentTypes.SLASH_BLADE_LOGIC, outLogic);

        // SE 继承

        ItemEnchantments outEnchantments = out.getTagEnchantments();
        ItemEnchantments inputEnchantments = input.getTagEnchantments();

        ItemEnchantments.Mutable mutableEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

        Set<Holder<Enchantment>> collect = Stream.concat(outEnchantments.keySet().stream(), inputEnchantments.keySet().stream()).collect(Collectors.toSet());
        for(Holder<Enchantment> enchantmentHolder : collect) {
            int outLevel = outEnchantments.getLevel(enchantmentHolder);
            int inputLevel = inputEnchantments.getLevel(enchantmentHolder);

            int level = Math.max(outLevel, inputLevel - 1); // TODO 配置

            if (level > 0) {
                mutableEnchantments.set(enchantmentHolder, level);
            }
        }

        EnchantmentHelper.setEnchantments(out, mutableEnchantments.toImmutable());

        return out;
    }
}
