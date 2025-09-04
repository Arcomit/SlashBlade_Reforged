package mod.slashblade.reforged.content.init;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.capabilitie.IPlayerInputCapability;
import mod.slashblade.reforged.content.data.network.KeyInputPack;
import mod.slashblade.reforged.content.entity.LightningEntity;
import mod.slashblade.reforged.content.entity.SummondSwordEntity;
import mod.slashblade.reforged.utils.constant.ByteBufCodecConstants;
import mod.slashblade.reforged.utils.helper.SwordsmanHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = SlashbladeMod.MODID)
public class SbRegisterPayloads {


    public static void register(IEventBus bus) {
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar payloadRegistrar = event.registrar(SlashbladeMod.getInstance().getModContainer().getModInfo().getVersion().getQualifier());

       /* payloadRegistrar.playBidirectional(
                SummoningSummondSwordPack.TYPE,
                ByteBufCodecConstants.SUMMONING_SUMMOND_SWORD_PACK,
                new DirectionalPayloadHandler<>(
                        SbRegisterPayloads::noClientInvocation,
                        (payload, context) -> context.enqueueWork(
                                        () -> {
                                            Player player = context.player();
                                            ItemStack mainHandItem = player.getMainHandItem();
                                            SlashBladeLogic slashBladeLogic = mainHandItem.get(SbDataComponents.SLASH_BLADE_LOGIC);
                                            if (slashBladeLogic == null) {
                                                return;
                                            }

                                            SummondSwordEntity summondSwordEntity = new SummondSwordEntity(SbEntityType.SUMMOND_SWORD_ENTITY.get(), player.level(), player);
                                            summondSwordEntity.setDamage(0.2f); //TODO 从配置加载
                                            summondSwordEntity.setMaxLifeTime(100);
                                            summondSwordEntity.lookAt(SwordsmanHelper.getAttackPos(player, slashBladeLogic), false);
                                            summondSwordEntity.attackActionCallbackPoint.register(e -> {
                                                LightningEntity lightningEntity = new LightningEntity(SbEntityType.LIGHTNING_ENTITY.get(), player.level(), player);
                                                lightningEntity.setDamage(0.2f); //TODO 从配置加载
                                                lightningEntity.setPos(e.getX(), e.getY(), e.getZ());
                                                player.level().addFreshEntity(lightningEntity);
                                            });
                                            player.level().addFreshEntity(summondSwordEntity);
                                            player.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 0.2F, 1.45F);
                                        }
                                )
                                .exceptionally(
                                        e -> {
                                            SlashbladeMod.LOGGER.error("Failed to handle SummoningSummondSwordPack: {}", e.getMessage(), e);
                                            return null;
                                        }
                                )
                )
        );

        payloadRegistrar.playBidirectional(
                SpecialOperationPack.TYPE,
                ByteBufCodecConstants.SPECIAL_OPERATION_PACK,
                new DirectionalPayloadHandler<>(
                        SbRegisterPayloads::noClientInvocation,
                        (payload, context) -> context.enqueueWork(
                                        () -> {
                                            //TODO 特殊行动带实现
                                        }
                                )
                                .exceptionally(
                                        e -> {
                                            SlashbladeMod.LOGGER.error("Failed to handle SpecialOperationPack: {}", e.getMessage(), e);
                                            return null;
                                        }
                                )
                )
        );*/

        payloadRegistrar.playBidirectional(
                KeyInputPack.TYPE,
                ByteBufCodecConstants.KEY_INPUT_PACK,
                new DirectionalPayloadHandler<>(
                        SbRegisterPayloads::noClientInvocation,
                        (payload, context) -> context.enqueueWork(
                                        () -> {
                                            Player player = context.player();
                                            IPlayerInputCapability capability = player.getCapability(SbCapabilities.PLAYER_INPUT_CAPABILITY);
                                            if (capability == null) {
                                                SlashbladeMod.LOGGER.warn("player {} does not have the PlayerInputCapability", player.getDisplayName().getString());
                                                return;
                                            }
                                            capability.acceptNewInput(payload);
                                        }
                                )
                                .exceptionally(
                                        e -> {
                                            SlashbladeMod.LOGGER.error("Failed to handle KeyInputPack: {}", e.getMessage(), e);
                                            return null;
                                        }
                                )
                )
        );
    }

    private static <D> void noClientInvocation(D payload, IPayloadContext context) {
        SlashbladeMod.LOGGER.warn("Payload {} should not be handled on the client side", payload.getClass().getSimpleName());
    }
}
