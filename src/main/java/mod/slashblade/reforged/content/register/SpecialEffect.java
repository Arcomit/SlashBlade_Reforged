package mod.slashblade.reforged.content.register;

import lombok.Getter;
import mod.slashblade.reforged.content.init.SbRegistrys;
import net.minecraft.Util;
import org.jetbrains.annotations.Nullable;

@Getter
public class SpecialEffect {

    int maxLevel;

    boolean notReplicable;

    @Nullable
    String descriptionId;

    @Nullable
    String docDescriptionId;

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("se", SbRegistrys.SPECIAL_EFFECT_REGISTRY.getKey(this));
        }
        return this.descriptionId;
    }

    public String getDocDescriptionId() {
        if (this.docDescriptionId == null) {
            this.docDescriptionId = Util.makeDescriptionId("se.doc", SbRegistrys.SPECIAL_EFFECT_REGISTRY.getKey(this));
        }
        return this.docDescriptionId;
    }

    public boolean isReplicable() {
        return !notReplicable;
    }

}
