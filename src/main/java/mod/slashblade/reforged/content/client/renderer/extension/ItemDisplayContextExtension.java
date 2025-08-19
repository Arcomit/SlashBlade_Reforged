package mod.slashblade.reforged.content.client.renderer.extension;

import net.minecraft.world.item.ItemDisplayContext;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-19 10:54
 * @Description: TODO
 */
public class ItemDisplayContextExtension {

    public static boolean thirdPerson(ItemDisplayContext transform){
        return  transform == ItemDisplayContext.THIRD_PERSON_LEFT_HAND ||
                transform == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }
}
