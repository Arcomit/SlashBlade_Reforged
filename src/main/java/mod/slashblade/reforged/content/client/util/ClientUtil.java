package mod.slashblade.reforged.content.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class ClientUtil {
    @Nullable
    public static Entity getEntityById(int id) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }
        return level.getEntity(id);
    }

}
