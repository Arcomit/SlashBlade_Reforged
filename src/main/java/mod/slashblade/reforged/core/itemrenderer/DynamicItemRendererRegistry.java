package mod.slashblade.reforged.core.itemrenderer;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-10 19:58
 * @Description: TODO
 */
public class DynamicItemRendererRegistry {

    public  static final DynamicItemRendererRegistry    INSTANCE  = new DynamicItemRendererRegistry();
    private static final Map<Item, DynamicItemRenderer> RENDERERS = new HashMap<>();

    public void register(ItemLike item, DynamicItemRenderer renderer) {
        Objects.requireNonNull(item,          "item is null");
        Objects.requireNonNull(item.asItem(), "item is null");
        Objects.requireNonNull(renderer,      "renderer is null");

        if (RENDERERS.putIfAbsent(item.asItem(), renderer) != null) {
            throw new IllegalArgumentException("Item " + BuiltInRegistries.ITEM.getKey(item.asItem()) + " already has a builtin renderer!");
        }
    }

    public DynamicItemRenderer get(ItemLike item) {
        Objects.requireNonNull(item.asItem(), "item is null");

        return RENDERERS.get(item.asItem());
    }

}
