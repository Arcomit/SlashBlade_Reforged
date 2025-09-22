package mod.slashblade.reforged.content.capability;

import com.google.common.collect.Range;
import mod.slashblade.reforged.content.data.ConcentrationRanks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface IConcentrationRank {

    long getRawRankPoint();
    void setRawRankPoint(long point);

    long getLastUpdate();
    void setLastUpdte(long time);

    long getLastRankRise();
    void setLastRankRise(long time);

    long getUnitCapacity();
    default long getMaxCapacity(){
        return (long)(ConcentrationRanks.MAX_LEVEL * getUnitCapacity()) - 1;
    }

    default ConcentrationRanks getRank(long time) {
        return ConcentrationRanks.getRankFromLevel(getRankLevel(time));
    }

    default long reductionLimitter(long reduction) {
        long limit = getRawRankPoint() % getUnitCapacity();

        return Math.min(reduction, limit);
    }

    default float getRankLevel(long currentTime) {
        return getRankPoint(currentTime) / (float) getUnitCapacity();
    }

    default float getRankProgress(long currentTime) {
        float level = getRankLevel(currentTime);

        Range<Float> range = getRank(currentTime).pointRange;

        double bottom = range.hasLowerBound() ?
                range.lowerEndpoint()
                : 0;

        double top = range.hasUpperBound() ?
                range.upperEndpoint()
                : Math.floor(bottom + 1.0f);

        double len = top - bottom;

        return (float)((level - bottom) / len);
    }

    default long getRankPoint(long time){
        long reduction = time - getLastUpdate();
        return getRawRankPoint() - reductionLimitter(reduction);
    }

    /*default void addRankPoint(LivingEntity user, long point){
        long time = user.level().getGameTime();

        ConcentrationRanks oldRank = getRank(time);

        this.setRawRankPoint(Math.min(Math.max(0 , point + getRankPoint(time)),getMaxCapacity()));
        this.setLastUpdte(time);

        if(oldRank.level < getRank(time).level)
            this.setLastRankRise(time);

        if(user instanceof ServerPlayer && !user.level().isClientSide){
            if(((ServerPlayer)user).connection == null) return;

            RankSyncMessage msg = new RankSyncMessage();
            msg.rawPoint = this.getRawRankPoint();
            NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(()->(ServerPlayer)user), msg);
        }
    }

    default void addRankPoint(DamageSource src){
        if (!(src.getEntity() instanceof LivingEntity)) return;

        LivingEntity user = (LivingEntity) src.getEntity();

        ItemStack stack = user.getMainHandItem();

        Optional<ComboState> combo = stack
                .getCapability(ItemSlashBlade.BLADESTATE)
                .map(s->s.resolvCurrentComboState(user));

        float modifier = combo
                .map(c -> getRankPointModifier(c))
                .orElse(getRankPointModifier(src));

        addRankPoint(user, (long)(modifier * getUnitCapacity()));
    }

    float getRankPointModifier(DamageSource ds);
    float getRankPointModifier(ComboState combo);*/
}
