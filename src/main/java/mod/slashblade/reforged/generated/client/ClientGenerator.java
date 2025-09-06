package mod.slashblade.reforged.generated.client;

import mod.slashblade.reforged.SlashbladeMod;
import mod.slashblade.reforged.utils.tuple.Tuple2;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@EventBusSubscriber(modid = SlashbladeMod.MODID, value = Dist.CLIENT)
public class ClientGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {

        List<LanguageItem> languageItemList = Arrays.stream(LanguageItems.class.getDeclaredFields())
                .filter(field -> field.getType().equals(LanguageItem.class))
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> Modifier.isFinal(field.getModifiers()))
                .peek(field -> field.setAccessible(true))
                .map(
                        field -> {
                            try {
                                return field.get(null);
                            } catch (IllegalAccessException e) {
                                SlashbladeMod.LOGGER.warn("", e);
                                return null;
                            }
                        }
                )
                .filter(Objects::nonNull)
                .filter(o -> o instanceof LanguageItem)
                .map(o -> (LanguageItem) o)
                .toList();

        Arrays.stream(LanguageTypes.values())
                .map(
                        type -> new LanguageProvider(event.getGenerator().getPackOutput(), SlashbladeMod.MODID, type.getLocale()) {
                            @Override
                            protected void addTranslations() {
                                languageItemList.stream()
                                        .map(i -> new Tuple2<>(i.getKey(), i.getTranslations().get(type)))
                                        .filter(t -> t.getB() != null)
                                        .forEach(t -> add(t.getA(), t.getB()));
                            }
                        }
                )
                .forEach(l -> event.getGenerator().addProvider(true, l));

    }

}
