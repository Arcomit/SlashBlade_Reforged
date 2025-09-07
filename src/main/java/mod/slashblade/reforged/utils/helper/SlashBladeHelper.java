package mod.slashblade.reforged.utils.helper;

import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SlashBladeHelper {

    public static boolean meetConditions(@NotNull ItemStack input, @NotNull SlashBladeLogic inputLogic, @NotNull ItemStack model, @NotNull SlashBladeLogic modelLogic) {

        if (!inputLogic.meetConditions(modelLogic)) {
            return false;
        }

        ItemEnchantments inputEnchantments = input.getTagEnchantments();
        ItemEnchantments modelEnchantments = model.getTagEnchantments();

        for(Holder<Enchantment> enchantmentHolder : modelEnchantments.keySet()) {
            int inputLevel = inputEnchantments.getLevel(enchantmentHolder);
            int modelLevel = modelEnchantments.getLevel(enchantmentHolder);

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
                .glory(inputLogic.getGlory())
                .refine(inputLogic.getRefine())
                .killCount(inputLogic.getKillCount())
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
