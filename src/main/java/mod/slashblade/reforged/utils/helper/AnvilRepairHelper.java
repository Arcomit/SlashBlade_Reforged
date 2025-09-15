package mod.slashblade.reforged.utils.helper;


import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeMaterial;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class AnvilRepairHelper {

    @SubscribeEvent
    public static void onAnvilUpdateEvent(AnvilUpdateEvent event) {

        Player player = event.getPlayer();

        ItemStack leftItemStack = event.getLeft();
        ItemStack rightItemStack = event.getRight();

        if (leftItemStack.isEmpty() || rightItemStack.isEmpty()) {
            return;
        }

        SlashBladeLogic slashBladeLogic = leftItemStack.get(SbDataComponentTypes.SLASH_BLADE_LOGIC);

        if (slashBladeLogic == null) {
            return;
        }

        SlashBladeMaterial slashBladeMaterial = rightItemStack.get(SbDataComponentTypes.SLASH_BLADE_MATERIAL);
        if (slashBladeMaterial == null) {
            return;
        }

        int cost = rightItemStack.getCount();
        if (slashBladeLogic.getDurable() < slashBladeLogic.getMaxDurable()) {
            double lossDurable = slashBladeLogic.getMaxDurable() - slashBladeLogic.getDurable();
            cost = Math.min(cost, (int) Math.ceil(lossDurable / slashBladeMaterial.getRepairDamageValue()));
        }

        ItemStack result = leftItemStack.copy();

        SlashBladeLogic.SlashBladeLogicBuilder builder = slashBladeLogic.toBuilder();

        builder.refine(slashBladeLogic.getRefine() + cost);
        builder.proudSoul(slashBladeLogic.getProudSoul() + cost * slashBladeMaterial.getAddProudSoul());

        double durable = slashBladeLogic.getDurable() + cost * slashBladeMaterial.getRepairDamageValue();
        builder.durable(Math.min(slashBladeLogic.getMaxDurable(), slashBladeLogic.getDurable() + cost * slashBladeMaterial.getRepairDamageValue()));

        if (!slashBladeLogic.isSpecialRepair() && slashBladeLogic.isBroken() && durable >= slashBladeLogic.getMaxDurable()) {
            builder.broken(false);
        }

        result.set(SbDataComponentTypes.SLASH_BLADE_LOGIC, builder.build());
        event.setMaterialCost(cost);
        event.setCost(1);
        event.setOutput(result);
    }


}
