package mod.slashblade.reforged.utils.constant;

import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

/**
 * @Author: til
 * @Description: EntityDataSerializer 常量类
 */
public class EntityDataSerializerConstants {

    public static final EntityDataSerializer<ResourceLocation> RESOURCE_LOCATION = EntityDataSerializer.forValueType(ResourceLocation.STREAM_CODEC);
    public static final EntityDataSerializer<SummondSwordEntity.ActionType> SUMMOND_SWORD_ENTITY_ACTION_TYPE = EntityDataSerializer.forValueType(ByteBufCodecConstants.SUMMOND_SWORD_ENTITY_ACTION_TYPE);
    public static final EntityDataSerializer<Color> COLOR = EntityDataSerializer.forValueType(ByteBufCodecConstants.COLOR);

}
