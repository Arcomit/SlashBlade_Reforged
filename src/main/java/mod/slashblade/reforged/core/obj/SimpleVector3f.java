package mod.slashblade.reforged.core.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joml.Vector3f;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 16:19
 * @Description: 简单三维向量类
 */
@AllArgsConstructor@Getter
public class SimpleVector3f {

    private final float x, y, z;

    public Vector3f toJoml() {
        return new Vector3f(x, y, z);
    }
}
