package mod.slashblade.reforged.core.animation.pojo.exclusion;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-05 17:08
 * @Description: 服务端不需要动画变换的数据信息。
 */
public class ServerExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(ClientOnly.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
