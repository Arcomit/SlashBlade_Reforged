package mod.slashblade.reforged.core.obj;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 12:08
 * @Description: 模型主类
 */
@Getter
public class ObjModel {

    private final Map<String, ObjGroup> Groups = new HashMap<>();

    public ObjGroup getGroup(String groupName) {
        return Groups.getOrDefault(groupName, null);
    }


}
