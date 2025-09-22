package mod.slashblade.reforged.content.data;

import mod.slashblade.reforged.content.capability.IInputCapability;
import mod.slashblade.reforged.content.data.network.KeyInputPack;
import mod.slashblade.reforged.content.event.key.KeyInputEvent;
import mod.slashblade.reforged.content.event.key.KeyUpdateEvent;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.EnumMap;

public class InputCapability implements IInputCapability, INBTSerializable<Tag> {

    final LivingEntity livingEntity;
    final EnumMap<KeyInput, Boolean> downMap;
    final EnumMap<KeyInput, Integer> holdMap;
    final EnumMap<KeyInput, Boolean> holdMemoryMap;


    public InputCapability(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
        downMap = new EnumMap<>(KeyInput.class);
        holdMap = new EnumMap<>(KeyInput.class);
        holdMemoryMap = new EnumMap<>(KeyInput.class);
        for(KeyInput value : KeyInput.values()) {
            downMap.put(value, false);
            holdMap.put(value, 0);
            holdMemoryMap.put(value, false);
        }
    }

    @Override
    public void acceptNewInput(KeyInputPack keyInputPack) {
        EnumMap<KeyInput, Boolean> oldDown = new EnumMap<>(downMap);
        EnumMap<KeyInput, Boolean> newDown = keyInputPack.getIsDown();
        NeoForge.EVENT_BUS.post(new KeyUpdateEvent(livingEntity, this, newDown));

        for(KeyInput key : KeyInput.values()) {
            downMap.put(key, newDown.get(key));
        }

        for(KeyInput key : KeyInput.values()) {
            if (oldDown.get(key) && !newDown.get(key)) {
                if (isLongHold(key)) {
                    NeoForge.EVENT_BUS.post(new KeyInputEvent(livingEntity, this, key, KeyInputEvent.KeyType.HOLD_UP));
                }
                NeoForge.EVENT_BUS.post(new KeyInputEvent(livingEntity, this, key, KeyInputEvent.KeyType.UP));
                holdMap.put(key, 0);
                holdMemoryMap.put(key, false);
                continue;
            }
            if (!oldDown.get(key) && newDown.get(key)) {
                NeoForge.EVENT_BUS.post(new KeyInputEvent(livingEntity, this, key, KeyInputEvent.KeyType.DOWN));
                continue;
            }
            holdMap.put(key, holdMap.get(key) + 1);
            if (isLongHold(key) && !holdMemoryMap.get(key)) {
                NeoForge.EVENT_BUS.post(new KeyInputEvent(livingEntity, this, key, KeyInputEvent.KeyType.HOLD));
                holdMemoryMap.put(key, true);
            }
        }


    }

    @Override
    public boolean isDown(KeyInput keyInput) {
        return downMap.get(keyInput);
    }

    @Override
    public boolean isLongHold(KeyInput keyInput) {
        return isDown(keyInput) && holdMap.get(keyInput) > 20;
    }


    @Override
    public @UnknownNullability Tag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull Tag nbt) {

    }
}
