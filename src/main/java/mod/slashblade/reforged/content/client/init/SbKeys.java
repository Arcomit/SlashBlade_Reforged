package mod.slashblade.reforged.content.client.init;

import com.mojang.blaze3d.platform.InputConstants;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.KeyInput;
import mod.slashblade.reforged.content.data.network.KeyInputPack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = SlashbladeMod.MODID, value = Dist.CLIENT)
public class SbKeys {


    public static final Lazy<KeyMapping> SUMMONING_SUMMOND_SWORD = Lazy.of(
            () -> new KeyMapping(
                    SlashbladeMod.prefix("summoning_summond_sword").toString(),
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_MOUSE_BUTTON_3,
                    SlashbladeMod.MODID
            )
    );

    public static final Lazy<KeyMapping> SPECIAL_OPERATION = Lazy.of(
            () -> new KeyMapping(
                    SlashbladeMod.prefix("special_operation").toString(),
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    SlashbladeMod.MODID
            )
    );

    /**
     * InputKey 到 KeyMapping 的映射表
     * 包含原版按键和自定义按键的映射关系
     */
    public static final Lazy<Map<KeyInput, KeyMapping>> KEY_BINDINGS = Lazy.of(
            () -> Map.of(
                    // 原版移动按键映射
                    KeyInput.FORWARD, Minecraft.getInstance().options.keyUp,           // W - 前进
                    KeyInput.BACK, Minecraft.getInstance().options.keyDown,            // S - 后退
                    KeyInput.LEFT, Minecraft.getInstance().options.keyLeft,            // A - 左移
                    KeyInput.RIGHT, Minecraft.getInstance().options.keyRight,          // D - 右移
                    KeyInput.SNEAK, Minecraft.getInstance().options.keyShift,          // SHIFT - 潜行
                    KeyInput.JUMP, Minecraft.getInstance().options.keyJump,            // SPACE - 跳跃

                    // 自定义按键映射
                    KeyInput.SUMMONING_SUMMOND_SWORD, SUMMONING_SUMMOND_SWORD.get(),  // 鼠标中键 - 召唤剑
                    KeyInput.SPECIAL_OPERATION, SPECIAL_OPERATION.get()               // V - 特殊操作
            )
    );

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(SUMMONING_SUMMOND_SWORD.get());
        event.register(SPECIAL_OPERATION.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        // 只有在游戏世界存在且玩家存在时才发送按键状态
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null && minecraft.player != null) {
            EnumMap<KeyInput, Boolean> isDown = new EnumMap<>(KeyInput.class);
            KEY_BINDINGS.get().forEach((key, binding) -> isDown.put(key, binding.isDown()));
            PacketDistributor.sendToServer(new KeyInputPack(isDown));
        }
    }
    

}
