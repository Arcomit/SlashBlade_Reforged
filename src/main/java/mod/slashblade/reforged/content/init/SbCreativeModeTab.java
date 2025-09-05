package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.generated.client.LanguageItems;
import mod.slashblade.reforged.generated.SlashBladeItemStacks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import com.mojang.serialization.JsonOps;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SbCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SlashbladeMod.MODID);

    public static final Supplier<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register(SlashbladeMod.MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable(LanguageItems.SLASH_BLADE_ITEM_GROUP.getKey()))
            .icon(() -> new ItemStack(SbItems.SLASH_BLADE.get()))
            .displayItems(
                    (params, output) -> {
                        // 从数据包中读取ItemStack数据
                        loadItemStacksFromDataPack(params).forEach(output::accept);
                    }
            )
            .build()
    );


    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }

    /**
     * 从数据包中加载ItemStack数据
     * 读取 data/slashblade_reforged/group_items/ 目录下的所有 .json 文件
     */
    private static List<ItemStack> loadItemStacksFromDataPack(CreativeModeTab.ItemDisplayParameters params) {
        List<ItemStack> itemStacks = new ArrayList<>();
        
        try {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                ResourceManager resourceManager = server.getResourceManager();
                
                // 尝试读取所有 group_items 目录下的文件
                String basePath = "data/" + SlashbladeMod.MODID + "/group_items/";
                
                // 获取所有可能的资源位置
                resourceManager.listResources(basePath, location -> location.getPath().endsWith(".json"))
                        .forEach((location, resource) -> {
                            try (BufferedReader reader = resource.openAsReader()) {
                                String jsonContent = reader.lines().collect(Collectors.joining("\n"));
                                
                                // 使用 ItemStack.CODEC 解析 JSON
                                var jsonElement = JsonParser.parseString(jsonContent);
                                var result = ItemStack.CODEC.parse(JsonOps.INSTANCE, jsonElement);
                                
                                if (result.result().isPresent()) {
                                    itemStacks.add(result.result().get());
                                } else {
                                    SlashbladeMod.LOGGER.warn("无法解析ItemStack从文件: {}", location);
                                }
                            } catch (IOException e) {
                                SlashbladeMod.LOGGER.warn("读取数据包文件失败: {}", location, e);
                            }
                        });
            } else {
                // 如果服务器不可用，回退到直接从SlashBladeItemStacks读取
                SlashbladeMod.LOGGER.warn("服务器不可用，回退到直接读取SlashBladeItemStacks");
                return getAllSlashBladeItemStacksFallback();
            }
        } catch (Exception e) {
            SlashbladeMod.LOGGER.warn("从数据包加载ItemStack失败，使用回退方案", e);
            return getAllSlashBladeItemStacksFallback();
        }
        
        return itemStacks;
    }
    
    /**
     * 回退方案：直接从SlashBladeItemStacks类读取
     */
    private static List<ItemStack> getAllSlashBladeItemStacksFallback() {
        return Arrays.stream(SlashBladeItemStacks.class.getDeclaredFields())
                .filter(field -> Supplier.class.isAssignableFrom(field.getType()))
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> Modifier.isFinal(field.getModifiers()))
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        Object fieldValue = field.get(null);
                        if (fieldValue instanceof Supplier<?> supplier) {
                            Object suppliedValue = supplier.get();
                            if (suppliedValue instanceof ItemStack itemStack) {
                                return itemStack;
                            }
                        }
                    } catch (IllegalAccessException e) {
                        SlashbladeMod.LOGGER.warn("无法访问字段: " + field.getName(), e);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
