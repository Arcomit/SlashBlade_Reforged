package mod.slashblade.reforged.core.obj;

import java.util.HashMap;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-18 17:18
 * @Description: TODO
 */
public class ObjGroupIndexProvider{
    protected final HashMap<String, Integer> indexMap = new HashMap<>();

    public int getIndex(String groupName) {
        return indexMap.putIfAbsent(groupName, indexMap.size());
    }
}
