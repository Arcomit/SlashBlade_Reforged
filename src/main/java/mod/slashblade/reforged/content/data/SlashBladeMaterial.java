package mod.slashblade.reforged.content.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SlashBladeMaterial {
    @SaveField
    int addProudSoul;
    @SaveField
    double repairDamageValue;
}
