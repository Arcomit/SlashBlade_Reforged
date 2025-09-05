package mod.slashblade.reforged.content.animation;

import com.maydaymemory.mae.util.LongSupplier;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-03 17:55
 * @Description: TODO
 */
public class NotCountingPausedNanoTimeSupplier implements LongSupplier {
    long lastTime;
    long pausedTime;

    @Override
    public long getAsLong() {
        if (FMLLoader.getDist() == Dist.CLIENT){
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.isPaused()){
                pausedTime = System.nanoTime() - lastTime;
                return lastTime;
            }
        }
        lastTime = System.nanoTime() - pausedTime;
        return lastTime;
    }
}
