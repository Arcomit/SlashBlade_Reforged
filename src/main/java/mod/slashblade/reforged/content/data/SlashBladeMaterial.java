package mod.slashblade.reforged.content.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mod.slashblade.reforged.content.data.capabilitie.ISlashBladeMaterial;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SlashBladeMaterial implements ISlashBladeMaterial {
    @SaveField
    int addProudSoul;
    @SaveField
    double repairDamageValue;
}
