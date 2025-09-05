package mod.slashblade.reforged;

import com.mojang.logging.LogUtils;
import mod.slashblade.reforged.content.init.SbActions;
import mod.slashblade.reforged.content.init.SbAttachmentTypes;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbItems;
import lombok.Getter;
import mod.slashblade.reforged.content.config.SbConfig;
import mod.slashblade.reforged.content.init.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.util.Objects;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-06 14:59
 * @Description: 模组主类
 */
@Mod(SlashbladeMod.MODID)
public class SlashbladeMod {

    public static final String MODID = "slashblade_reforged";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Getter
    private static SlashbladeMod instance;

    @Getter
    private final ModContainer modContainer;

    public SlashbladeMod(IEventBus modEventBus, ModContainer modContainer) {
        instance = this;
        this.modContainer = modContainer;

        // 注册配置
        modContainer.registerConfig(ModConfig.Type.COMMON, SbConfig.COMMON_CONFIG);

        SbItems.register(modEventBus);
        SbActions.register(modEventBus);
        SbDataComponentTypes.register(modEventBus);
        SbAttachmentTypes.register(modEventBus);
        SbCapabilities.register(modEventBus);
        SbAttackTypes.register(modEventBus);
        SbEntityDataSerializers.register(modEventBus);
        SbEntityType.register(modEventBus);
        SbRegisterPayloads.register(modEventBus);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static ResourceLocation prefix(String... path) {
        Objects.requireNonNull(path, "path must not be null");
        return prefix(String.join("/", path));
    }

}
