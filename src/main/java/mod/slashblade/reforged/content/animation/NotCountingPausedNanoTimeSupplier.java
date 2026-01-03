package mod.slashblade.reforged.content.animation;

import com.maydaymemory.mae.util.LongSupplier;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-03 17:55
 * @Description: TODO
 */
@ThreadSafe
public class NotCountingPausedNanoTimeSupplier implements LongSupplier {
    // 状态跟踪变量
    private  long baseTime;
    private  long totalPausedDuration;
    private  long pauseStartTime = -1;  // -1表示未暂停
    private final Lock lock = new ReentrantLock();

    @Override
    public long getAsLong() {
        boolean isPaused = false;
        // 仅客户端检查暂停状态
        if (FMLLoader.getDist() == Dist.CLIENT) {
            final Minecraft mc = Minecraft.getInstance();
            isPaused = (mc != null) && mc.isPaused();
        }

        lock.lock();
        try {
            final long currentNanoTime = System.nanoTime();
            // 处理状态切换
            if (isPaused) {
                if (pauseStartTime < 0) {
                    // 新进入暂停状态：记录暂停开始时间
                    pauseStartTime = currentNanoTime;
                }
                // 返回暂停开始前的最新有效时间
                return baseTime;
            } else if (pauseStartTime >= 0) {
                // 刚从暂停恢复：累加暂停时间
                totalPausedDuration += (currentNanoTime - pauseStartTime);
                pauseStartTime = -1;  // 重置状态
            }

            // 更新基础时间（当前时间 - 总暂停时间）
            baseTime = currentNanoTime - totalPausedDuration;
            return baseTime;
        } finally {
            lock.unlock();
        }
    }
}
