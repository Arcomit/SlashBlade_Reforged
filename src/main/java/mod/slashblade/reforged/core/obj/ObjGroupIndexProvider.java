package mod.slashblade.reforged.core.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-18 17:18
 * @Description: TODO
 */
public class ObjGroupIndexProvider{

    private final Map<String, Integer> nameToIndex = new HashMap<>();
    private final List<String> indexToName = new ArrayList<>();

    public int getIndex(String groupName) {
        return nameToIndex.computeIfAbsent(groupName, k -> {
            indexToName.add(k);
            return indexToName.size() - 1;
        });
    }

    public String getGroupName(int index) {
        return (index >= 0 && index < indexToName.size())
                ? indexToName.get(index)
                : "Default";
    }
}
