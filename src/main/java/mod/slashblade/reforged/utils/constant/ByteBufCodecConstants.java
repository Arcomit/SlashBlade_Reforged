package mod.slashblade.reforged.utils.constant;


import io.netty.buffer.ByteBuf;
import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.client.util.ClientUtil;
import mod.slashblade.reforged.content.data.*;
import mod.slashblade.reforged.content.data.network.KeyInputPack;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.content.init.SbRegistrys;
import mod.slashblade.reforged.content.recipe.*;
import mod.slashblade.reforged.content.register.SpecialAttack;
import mod.slashblade.reforged.content.register.SpecialEffect;
import mod.slashblade.reforged.utils.Util;
import mod.slashblade.reforged.utils.tuple.Tuple2;
import mod.slashblade.reforged.utils.tuple.Tuple3;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: til
 * @Description: StreamCodec 编解码常量类
 */
public class ByteBufCodecConstants {

    public static final Map<Class<?>, StreamCodec<ByteBuf, ?>> BASIC_TYPE_CODEC_MAP = new HashMap<>();

    public static final StreamCodec<ByteBuf, SummondSwordEntity.ActionType> SUMMOND_SWORD_ENTITY_ACTION_TYPE = new EnumStreamCodec<>(SummondSwordEntity.ActionType.class);
    public static final StreamCodec<ByteBuf, Color> COLOR = new StreamCodec<>() {
        @Override
        public @NotNull Color decode(@NotNull ByteBuf buffer) {
            return new Color(buffer.readInt(), true);
        }

        @Override
        public void encode(@NotNull ByteBuf buffer, @NotNull Color value) {
            buffer.writeInt(value.getRGB());
        }
    };
    public static final StreamCodec<ByteBuf, Entity> ENTITY_CLIENT_SIDE = new StreamCodec<ByteBuf, Entity>() {
        @SuppressWarnings("NullableProblems")
        @Override
        @Nullable
        public Entity decode(ByteBuf buffer) {
            return ClientUtil.getEntityById(buffer.readInt());
        }

        @Override
        public void encode(ByteBuf buffer, @Nullable Entity value) {
            buffer.writeInt(
                    value == null
                            ? -1
                            : value.getId()
            );
        }
    };
    public static final StreamCodec<ByteBuf, SlashBladeLogic> SLASH_BLADE_LOGIC = new DataStreamCodec<>(SlashBladeLogic.class);
    public static final StreamCodec<ByteBuf, SlashBladeStyle> SLASH_BLADE_STYLE = new DataStreamCodec<>(SlashBladeStyle.class);
    public static final StreamCodec<ByteBuf, SlashBladeMaterial> SLASH_BLADE_MATERIAL = new DataStreamCodec<>(SlashBladeMaterial.class);
    public static final StreamCodec<ByteBuf, LockTarget> LOCK_TARGET = new DataStreamCodec<>(LockTarget.class);

    public static final StreamCodec<ByteBuf, KeyInput> KEY_INPUT = new EnumStreamCodec<>(KeyInput.class);
    public static final StreamCodec<ByteBuf, EnumMap<KeyInput, Boolean>> KEY_INPUT_MAP = new StreamCodec<>() {
        @Override
        public @NotNull EnumMap<KeyInput, Boolean> decode(@NotNull ByteBuf buffer) {
            EnumMap<KeyInput, Boolean> isDown = new EnumMap<>(KeyInput.class);
            for(KeyInput value : KeyInput.values()) {
                isDown.put(value, buffer.readBoolean());
            }
            return isDown;
        }

        @Override
        public void encode(@NotNull ByteBuf buffer, @NotNull EnumMap<KeyInput, Boolean> value) {
            for(KeyInput keyInput : KeyInput.values()) {
                buffer.writeBoolean(value.get(keyInput));
            }
        }
    };
    public static final StreamCodec<ByteBuf, KeyInputPack> KEY_INPUT_PACK = new StreamCodec<>() {
        @Override
        public @NotNull KeyInputPack decode(@NotNull ByteBuf buffer) {
            return new KeyInputPack(KEY_INPUT_MAP.decode(buffer));
        }

        @Override
        public void encode(@NotNull ByteBuf buffer, @NotNull KeyInputPack value) {
            KEY_INPUT_MAP.encode(buffer, value.getIsDown());
        }
    };
    public static final StreamCodec<RegistryFriendlyByteBuf, IRecipeInputItem> RECIPE_INPUT_ITEM = new StreamCodec<>() {
        @Override
        public @NotNull IRecipeInputItem decode(@NotNull RegistryFriendlyByteBuf buffer) {
            boolean isNull = buffer.readBoolean();
            if (isNull) {
                return IRecipeInputItem.EMPTY;
            }
            ResourceLocation key = buffer.readResourceLocation();
            IRecipeInputItemSerializer<?> iRecipeInputItemSerializer = SbRegistrys.RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY.get(key);
            if (iRecipeInputItemSerializer == null) {
                return IRecipeInputItem.EMPTY;
            }
            return iRecipeInputItemSerializer.streamCodec().decode(buffer);
        }

        @Override
        public void encode(@NotNull RegistryFriendlyByteBuf buffer, @NotNull IRecipeInputItem value) {
            ResourceLocation key = SbRegistrys.RECIPE_INPUT_ITEM_SERIALIZER_REGISTRY.getKey(value.getSerializer());
            if (key == null) {
                buffer.writeBoolean(true);
                return;
            }
            buffer.writeBoolean(false);
            buffer.writeResourceLocation(key);
            value.getSerializer().streamCodec().encode(buffer, Util.forcedConversion(value));
        }
    };

    public static final StreamCodec<RegistryFriendlyByteBuf, IRecipeInputItem.IngredientRecipeInputItem> INGREDIENT_RECIPE_INPUT_ITEM = Ingredient.CONTENTS_STREAM_CODEC
            .map(IRecipeInputItem.IngredientRecipeInputItem::new, IRecipeInputItem.IngredientRecipeInputItem::getIngredient);
    public static final StreamCodec<RegistryFriendlyByteBuf, IRecipeInputItem.SlashBladeRecipeInputItem> SLASH_BLADE_RECIPE_INPUT_ITEM = ItemStack.STREAM_CODEC
            .map(IRecipeInputItem.SlashBladeRecipeInputItem::new, IRecipeInputItem.SlashBladeRecipeInputItem::getItemStack);
    public static final StreamCodec<RegistryFriendlyByteBuf, IRecipeInputItem.EnchantmentItemRecipeInputItem> ENCHANTMENT_ITEM_RECIPE_INPUT_ITEM = StreamCodec.composite(
            RECIPE_INPUT_ITEM,
            IRecipeInputItem.EnchantmentItemRecipeInputItem::getBase,
            ItemEnchantments.STREAM_CODEC,
            IRecipeInputItem.EnchantmentItemRecipeInputItem::getEnchantments,
            IRecipeInputItem.EnchantmentItemRecipeInputItem::new
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SlashBladeRecipe> SLASH_BLADE_RECIPE = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
            SlashBladeRecipe::getPattern,

            ByteBufCodecs.map(
                    java.util.HashMap::new,
                    ByteBufCodecs.VAR_INT.map(i -> (char) i.intValue(), c -> (int) c),
                    RECIPE_INPUT_ITEM
            ),
            SlashBladeRecipe::getKey,

            // mainSlashBladeKey: char
            ByteBufCodecs.VAR_INT.map(i -> (char) i.intValue(), c -> (int) c),
            SlashBladeRecipe::getMainSlashBladeKey,

            // result: ItemStack
            ItemStack.STREAM_CODEC,
            SlashBladeRecipe::getResult,

            SlashBladeRecipe::new
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentTagIngredient> ENCHANTMENT_TAG_INGREDIENT = StreamCodec.composite(
            RECIPE_INPUT_ITEM,
            EnchantmentTagIngredient::getIngredient,

            ByteBufCodecs.BOOL,
            EnchantmentTagIngredient::isInheritance,

            EnchantmentTagIngredient::new
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ProudSoulShapelessRecipe> PROUD_SOUL_SHAPELESS_RECIPE = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ProudSoulShapelessRecipe::getGroup,

            CraftingBookCategory.STREAM_CODEC,
            ProudSoulShapelessRecipe::category,

            ItemStack.STREAM_CODEC,
            ProudSoulShapelessRecipe::getResult,

            ByteBufCodecs.collection(
                    ArrayList::new,
                    ENCHANTMENT_TAG_INGREDIENT
            ).map(
                    a -> NonNullList.of(EnchantmentTagIngredient.EMPTY, a.toArray(EnchantmentTagIngredient[]::new)),
                    ArrayList::new
            ),
            ProudSoulShapelessRecipe::getTagIngredients,


            ProudSoulShapelessRecipe::new
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ProudSoulShapedRecipe> PROUD_SOUL_SHAPED_RECIPE = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
            ProudSoulShapedRecipe::getPattern,

            ByteBufCodecs.map(
                    HashMap::new,
                    ByteBufCodecs.VAR_INT.map(i -> (char) i.intValue(), c -> (int) c),
                    RECIPE_INPUT_ITEM
            ),
            ProudSoulShapedRecipe::getKey,

            ByteBufCodecs.collection(
                    ArrayList::new,
                    ByteBufCodecs.VAR_INT.map(i -> (char) i.intValue(), c -> (int) c)
            ),
            ProudSoulShapedRecipe::getInheritanceKey,

            // result: ItemStack
            ItemStack.STREAM_CODEC,
            ProudSoulShapedRecipe::getResult,

            ProudSoulShapedRecipe::new
    );

    public static final RegistryStreamCodec<SpecialAttack> SA = new RegistryStreamCodec<>(SbRegistrys.SPECIAL_ATTACK_REGISTRY);
    public static final RegistryStreamCodec<SpecialEffect> SE = new RegistryStreamCodec<>(SbRegistrys.SPECIAL_EFFECT_REGISTRY);
    public static final StreamCodec<ByteBuf, Map<SpecialEffect, Integer>> SPECIAL_EFFECT_LEVEL_MAP = ByteBufCodecs.map(HashMap::new, SE, ByteBufCodecs.INT);

    static {
        // 基本数值类型
        BASIC_TYPE_CODEC_MAP.put(boolean.class, ByteBufCodecs.BOOL);
        BASIC_TYPE_CODEC_MAP.put(Boolean.class, ByteBufCodecs.BOOL);

        BASIC_TYPE_CODEC_MAP.put(byte.class, ByteBufCodecs.BYTE);
        BASIC_TYPE_CODEC_MAP.put(Byte.class, ByteBufCodecs.BYTE);

        BASIC_TYPE_CODEC_MAP.put(short.class, ByteBufCodecs.SHORT);
        BASIC_TYPE_CODEC_MAP.put(Short.class, ByteBufCodecs.SHORT);

        BASIC_TYPE_CODEC_MAP.put(int.class, ByteBufCodecs.INT);
        BASIC_TYPE_CODEC_MAP.put(Integer.class, ByteBufCodecs.INT);

        BASIC_TYPE_CODEC_MAP.put(long.class, ByteBufCodecs.VAR_LONG);
        BASIC_TYPE_CODEC_MAP.put(Long.class, ByteBufCodecs.VAR_LONG);

        BASIC_TYPE_CODEC_MAP.put(float.class, ByteBufCodecs.FLOAT);
        BASIC_TYPE_CODEC_MAP.put(Float.class, ByteBufCodecs.FLOAT);

        BASIC_TYPE_CODEC_MAP.put(double.class, ByteBufCodecs.DOUBLE);
        BASIC_TYPE_CODEC_MAP.put(Double.class, ByteBufCodecs.DOUBLE);

        BASIC_TYPE_CODEC_MAP.put(String.class, ByteBufCodecs.STRING_UTF8);
        BASIC_TYPE_CODEC_MAP.put(byte[].class, ByteBufCodecs.BYTE_ARRAY);

        BASIC_TYPE_CODEC_MAP.put(CompoundTag.class, ByteBufCodecs.COMPOUND_TAG);
        BASIC_TYPE_CODEC_MAP.put(Tag.class, ByteBufCodecs.TAG);

        BASIC_TYPE_CODEC_MAP.put(Vector3f.class, ByteBufCodecs.VECTOR3F);
        BASIC_TYPE_CODEC_MAP.put(Quaternionf.class, ByteBufCodecs.QUATERNIONF);

        BASIC_TYPE_CODEC_MAP.put(ResourceLocation.class, ResourceLocation.STREAM_CODEC);

        // 自定义数据类型
        BASIC_TYPE_CODEC_MAP.put(SlashBladeLogic.class, SLASH_BLADE_LOGIC);
        BASIC_TYPE_CODEC_MAP.put(SlashBladeStyle.class, SLASH_BLADE_STYLE);

        BASIC_TYPE_CODEC_MAP.put(Entity.class, ENTITY_CLIENT_SIDE);

        BASIC_TYPE_CODEC_MAP.put(SpecialAttack.class, SA);
        BASIC_TYPE_CODEC_MAP.put(SpecialEffect.class, SE);

    }

    public static class CanBeNullStreamCodec<O> implements StreamCodec<ByteBuf, O> {

        final StreamCodec<ByteBuf, O> basicTypeCodec;

        public CanBeNullStreamCodec(StreamCodec<ByteBuf, O> basicTypeCodec) {
            this.basicTypeCodec = basicTypeCodec;
        }

        @SuppressWarnings("NullableProblems")
        @Nullable
        @Override
        public O decode(@NotNull ByteBuf buffer) {
            if (buffer.readBoolean()) {
                return null;
            }

            return basicTypeCodec.decode(buffer);
        }

        @Override
        public void encode(@NotNull ByteBuf buffer, @Nullable O value) {

            if (value == null) {
                buffer.writeBoolean(true);
                return;
            }

            buffer.writeBoolean(false);
            basicTypeCodec.encode(buffer, value);

        }
    }

    public static class EnumStreamCodec<E extends Enum<E>> implements StreamCodec<ByteBuf, E> {
        private final Class<E> enumClass;

        public EnumStreamCodec(Class<E> enumClass) {
            this.enumClass = enumClass;
        }

        @NotNull
        @Override
        public E decode(@NotNull ByteBuf byteBuf) {
            int in = byteBuf.readByte();
            return enumClass.getEnumConstants()[in];
        }

        @Override
        public void encode(@NotNull ByteBuf o, @NotNull E e) {
            o.writeByte(e.ordinal());
        }
    }

    public static class DataStreamCodec<D> implements StreamCodec<ByteBuf, D> {
        Class<D> clazz;
        boolean inited = false;
        List<Tuple2<Field, StreamCodec<ByteBuf, ?>>> fieldCodecList;

        public DataStreamCodec(Class<D> clazz) {
            this.clazz = clazz;
        }

        @NotNull
        @Override
        public D decode(@NotNull ByteBuf buffer) {

            init();
            D d;
            try {
                d = clazz.getConstructor().newInstance();
            } catch (Exception e) {
                SlashbladeMod.LOGGER.error("Failed to create instance of class {} during decode", clazz.getSimpleName(), e);
                //noinspection DataFlowIssue
                return null;
            }

            D finalD = d;
            fieldCodecList.forEach(tuple -> {
                try {
                    Field field = tuple.getA();
                    StreamCodec<ByteBuf, ?> codec = tuple.getB();
                    Object value = codec.decode(buffer);
                    field.set(finalD, value);
                } catch (Exception e) {
                    SlashbladeMod.LOGGER.error("Failed to decode field {} in class {}", tuple.getA().getName(), clazz.getSimpleName(), e);
                }
            });

            return d;
        }

        @Override
        public void encode(@NotNull ByteBuf buffer, @NotNull D value) {
            try {
                init();
                fieldCodecList.forEach(tuple -> {
                    try {
                        Field field = tuple.getA();
                        StreamCodec<ByteBuf, Object> codec = Util.forcedConversion(tuple.getB());
                        Object fieldValue = field.get(value);
                        codec.encode(buffer, fieldValue);
                    } catch (Exception e) {
                        SlashbladeMod.LOGGER.error("Failed to encode field {} in class {}", tuple.getA().getName(), clazz.getSimpleName(), e);
                    }
                });
            } catch (Exception e) {
                SlashbladeMod.LOGGER.error("Failed to initialize codec for {}", clazz.getSimpleName(), e);
            }
        }

        private void init() {
            if (inited) {
                return;
            }
            inited = true;

            fieldCodecList = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .filter(field -> !Modifier.isFinal(field.getModifiers()))
                    .map(field -> new Tuple2<>(field, field.getAnnotation(SaveField.class)))
                    .filter(t -> t.getB() != null)
                    .map(t -> new Tuple3<>(t.getA(), t.getB(), getStreamCodec(t.getA(), t.getB())))
                    .filter(t -> t.getC() != null)
                    .map(
                            t -> t.getB().canBeNull()
                                    ? new Tuple3<>(t.getA(), t.getB(), new CanBeNullStreamCodec<>(t.getC()))
                                    : t
                    )
                    .peek(t -> t.getA().setAccessible(true))
                    .map(t -> new Tuple2<Field, StreamCodec<ByteBuf, ?>>(t.getA(), t.getC()))
                    .collect(Collectors.toList());

        }

        @Nullable
        public StreamCodec<ByteBuf, ?> getStreamCodec(Field field, SaveField saveField) {
            // 首先检查是否有自定义编解码器方法
            if (!saveField.customCodecMethod().isEmpty()) {
                try {
                    // 尝试在当前类中查找自定义方法
                    Method method = clazz.getDeclaredMethod(saveField.customCodecMethod());
                    if (Modifier.isStatic(method.getModifiers())) {
                        method.setAccessible(true);
                        return Util.forcedConversion(method.invoke(null));
                    }
                } catch (Exception e) {
                    SlashbladeMod.LOGGER.warn("警告: 无法找到自定义编解码器方法: {} for field: {} in class: {}", saveField.customCodecMethod(), field.getName(), clazz.getSimpleName(), e);
                }
            }

            // 检查基础类型映射
            if (BASIC_TYPE_CODEC_MAP.containsKey(field.getType())) {
                return BASIC_TYPE_CODEC_MAP.get(field.getType());
            }

            // 如果是枚举类型，使用 EnumStreamCodec
            if (field.getType().isEnum()) {
                return new EnumStreamCodec<>(Util.forcedConversion(field.getType()));
            }

            // 默认返回 null，表示不支持该类型
            return null;
        }
    }

    public static class RegistryStreamCodec<R> implements StreamCodec<ByteBuf, R> {

        final Registry<R> registry;

        public RegistryStreamCodec(Registry<R> registry) {
            this.registry = registry;
        }

        @SuppressWarnings("NullableProblems")
        @Nullable
        @Override
        public R decode(@NotNull ByteBuf buffer) {
            Optional<Holder.Reference<R>> holder = registry.getHolder(buffer.readInt());
            return holder.map(Holder.Reference::value).orElse(null);
        }

        @Override
        public void encode(ByteBuf buffer, @NotNull R value) {
            buffer.writeInt(registry.getId(value));
        }
    }
}
