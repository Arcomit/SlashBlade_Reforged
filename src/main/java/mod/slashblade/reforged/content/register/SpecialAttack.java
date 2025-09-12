package mod.slashblade.reforged.content.register;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.content.init.SbRegistrys;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.Nullable;

public class SpecialAttack {

    @Nullable
    String descriptionId;

    @Nullable
    String docDescriptionId;

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("sa", SbRegistrys.SPECIAL_ATTACK_REGISTRY.getKey(this));
        }
        return this.descriptionId;
    }

    public String getDocDescriptionId() {
        if (this.docDescriptionId == null) {
            this.docDescriptionId = Util.makeDescriptionId("sa.doc", SbRegistrys.SPECIAL_ATTACK_REGISTRY.getKey(this));
        }
        return this.docDescriptionId;
    }
}
