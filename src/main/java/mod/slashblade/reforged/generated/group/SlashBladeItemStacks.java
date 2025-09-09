package mod.slashblade.reforged.generated.group;

import mod.slashblade.reforged.content.data.SlashBladeLogic;
import mod.slashblade.reforged.content.data.SlashBladeStyle;
import mod.slashblade.reforged.content.init.SbDataComponentTypes;
import mod.slashblade.reforged.content.init.SbItems;
import mod.slashblade.reforged.utils.constant.R;
import mod.slashblade.reforged.utils.constant.SlashBladeNameConstants;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * 集中管理所有拔刀剑相关的ItemStack供应商
 */
public class SlashBladeItemStacks {


    /***
     * 无铭「无名」
     */
    public static final Supplier<ItemStack> ANONYMITY_NAMELESS = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.ANONYMITY_NAMELESS)
                                    .maxDurable(512)
                                    .attack(4)
                                    .build()
                    )
                    .build();

    //无铭刀「木偶」 (Anonymity -Wood-)
    public static final Supplier<ItemStack> ANONYMITY_WOOD = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.ANONYMITY_WOOD)
                                    .attack(3)
                                    .maxDurable(512)
                                    .broken(true)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.wood$png)
                                    .build()
                    )
                    .build();

    //无铭刀「竹光」 (Anonymity -Bamboo Light-)
    public static final Supplier<ItemStack> ANONYMITY_BAMBOO_LIGHT = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.ANONYMITY_BAMBOO_LIGHT)
                                    .attack(3)
                                    .broken(true)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.bamboo$png)
                                    .build()
                    )
                    .build();

    //名刀「银纸竹光」 (Noted -Silver Bamboo Light-)
    public static final Supplier<ItemStack> NOTED_SILVER_BAMBOO_LIGHT = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.NOTED_SILVER_BAMBOO_LIGHT)
                                    .attack(4)
                                    .maxDurable(2048)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.silverbamboo$png)
                                    .build()
                    )
                    .build();

    //木刀「铁刀木」 (Ironwood -Tagayasan-)
    public static final Supplier<ItemStack> IRONWOOD_TAGAYASAN = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder() // TODO SA
                                    .key(SlashBladeNameConstants.IRONWOOD_TAGAYASAN)
                                    .attack(5)
                                    .maxDurable(8192)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.Named.tagayasan$png)
                                    .build()
                    )
                    .build();

    /***
     * 利刀「白鞘」
     */
    public static final Supplier<ItemStack> SHARPNESS_WHITE = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.SHARPNESS_WHITE)
                                    .attack(3)
                                    .maxDurable(1024)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.white$png)
                                    .build()
                    )
                    .build();

    //大太刀「」
    public static final Supplier<ItemStack> SLASH_BLADE = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.SLASH_BLADE)
                                    .attack(4)
                                    .build()
                    )
                    .build();

    //宝刀「付丧」结月 (Noble -Tukumo- Violet)
    public static final Supplier<ItemStack> NOBLE_TUKUMO_VIOLET = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder() // TODO SA
                                    .key(SlashBladeNameConstants.SLASH_BLADE)
                                    .attack(5)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .model(R.Slashblade.Models.Named.agito$obj)
                                    .texture(R.Slashblade.Models.Named.aTukumo$png)
                                    .build()
                    )
                    .build();

    //「千鹤」村正 (-Chizuru- Muramasa)
    public static final Supplier<ItemStack> CHIZURU_MURAMASA = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder() // TODO SA
                                    .key(SlashBladeNameConstants.CHIZURU_MURAMASA)
                                    .attack(5)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .model(R.Slashblade.Models.Named.Muramasa.muramasa$obj)
                                    .texture(R.Slashblade.Models.Named.Muramasa.muramasa$png)
                                    .build()
                    )
                    .build();

    //鞘 (Scabbard)
    //TODO 名刀「银纸竹光」断刀后获得鞘 要求击杀 > 100
    public static final Supplier<ItemStack> SCABBARD = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.SCABBARD)
                                    .attack(3)
                                    .sealed(true)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.scabbard$png)
                                    .build()
                    )
                    .build();

    //利刀「无名」红玉 (Sharpness -nameless- Ruby)
    public static final Supplier<ItemStack> SHARPNESS_NAMELESS_RUBY = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.SHARPNESS_NAMELESS_RUBY)
                                    .attack(4)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.ruby$png)
                                    .build()
                    )
                    .build();

    //狐月刀「黑狐」 (Crescent -BlackFoxes-)
    public static final Supplier<ItemStack> CRESCENT_BLACK_FOXES = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.CRESCENT_BLACK_FOXES)
                                    .attack(5)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.Named.Sange.sange$obj)
                                    .model(R.Slashblade.Models.Named.Sange.black$png)
                                    .build()
                    )
                    .build();

    //狐月刀「白狐」 (Crescent -WeissFox-)
    public static final Supplier<ItemStack> CRESCENT_WEISS_FOX = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.CRESCENT_WEISS_FOX)
                                    .attack(5)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.Named.Sange.sange$obj)
                                    .model(R.Slashblade.Models.Named.Sange.white$png)
                                    .build()
                    )
                    .build();

    //「击柝」露台 (-Wooden- Rodai)
    public static final Supplier<ItemStack> WOODEN_RODAI = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.WOODEN_RODAI)
                                    .attack(4)
                                    .maxDurable(2048)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.rodaiWooden$png)
                                    .build()
                    )
                    .build();

    //「锐岩」露台 (-Stone- Rodai)
    public static final Supplier<ItemStack> STONE_RODAI = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.STONE_RODAI)
                                    .attack(4f)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.rodaiStone$png)
                                    .build()
                    )
                    .build();

    //利刀「铁」露台 (Named -Steel- Rodai)
    public static final Supplier<ItemStack> NAMED_STEEL_RODAI = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .attack(4.5f)
                                    .key(SlashBladeNameConstants.NAMED_STEEL_RODAI)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.rodaiIron$png)
                                    .build()
                    )
                    .build();

    //宝刀「山吹」露台 (Named -Golden- Rodai)
    public static final Supplier<ItemStack> NAMED_GOLDEN_RODAI = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .attack(5f)
                                    .maxDurable(1024)
                                    .key(SlashBladeNameConstants.NAMED_GOLDEN_RODAI)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.rodaiGolden$png)
                                    .build()
                    )
                    .build();

    //名刀「金刚」露台 (Named -Diamond- Rodai)
    public static final Supplier<ItemStack> NAMED_DIAMOND_RODAI = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .attack(5f)
                                    .key(SlashBladeNameConstants.NAMED_DIAMOND_RODAI)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.rodaiDiamond$png)
                                    .build()
                    )
                    .build();

    //名刀「玄钰」露台 (Named -Netherite- Rodai)
    public static final Supplier<ItemStack> NAMED_NETHERITE_RODAI = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .attack(5f)
                                    .maxDurable(8192)
                                    .key(SlashBladeNameConstants.NAMED_NETHERITE_RODAI)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.rodaiNetherite$png)
                                    .build()
                    )
                    .build();

    // 锈刀 （Sabigatana）
    public static final Supplier<ItemStack> SABIGATANA = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .key(SlashBladeNameConstants.SABIGATANA)
                                    .attack(3f)
                                    .maxDurable(2048)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.Named.Muramasa.sabigatana$png)
                                    .model(R.Slashblade.Models.Named.Muramasa.muramasa$obj)
                                    .build()
                    )
                    .build();


    public static final Supplier<ItemStack> SABIGATANA_BROKEN = () ->
            new ItemStackBuilder(SABIGATANA.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            s -> s.toBuilder()
                                    .durable(0)
                                    .broken(true)
                                    .specialRepair(true)
                                    .build(),
                            SlashBladeLogic::new
                    )
                    .build();

    //刚剑「胴田贯」 (Steel -Doutanuki-)
    public static final Supplier<ItemStack> STEEL_DOUTANUKI = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder()
                                    .attack(4f)
                                    .key(SlashBladeNameConstants.STEEL_DOUTANUKI)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.Named.Muramasa.sabigatana$png)
                                    .model(R.Slashblade.Models.Named.Muramasa.muramasa$obj)
                                    .build()
                    )
                    .build();

    //枯石大刀 (Koseki)
    // TODO 凋零爆炸
    public static final Supplier<ItemStack> KOSEKI = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder() // TODO SA
                                    .attack(4f)
                                    .key(SlashBladeNameConstants.KOSEKI)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.Named.Dios.dios$png)
                                    .model(R.Slashblade.Models.Named.Dios.koseki$png)
                                    .build()
                    )
                    .build();

    //魔剑「阎魔刀」 (Yamato)
    public static final Supplier<ItemStack> YAMATO = () ->
            new ItemStackBuilder(SbItems.SLASH_BLADE.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            SlashBladeLogic.builder() // TODO SA
                                    .attack(4f)
                                    .key(SlashBladeNameConstants.YAMATO)
                                    .build()
                    )
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_STYLE.get(),
                            SlashBladeStyle.builder()
                                    .texture(R.Slashblade.Models.Named.yamato$png)
                                    .model(R.Slashblade.Models.Named.yamato$png)
                                    .build()
                    )
                    .build();

    // TODO 末影龙掉落
    public static final Supplier<ItemStack> YAMATO_BROKEN = () ->
            new ItemStackBuilder(YAMATO.get())
                    .set(
                            SbDataComponentTypes.SLASH_BLADE_LOGIC.get(),
                            s -> s.toBuilder()
                                    .durable(0)
                                    .broken(true)
                                    .specialRepair(true)
                                    .build(),
                            SlashBladeLogic::new
                    )
                    .build();
}
