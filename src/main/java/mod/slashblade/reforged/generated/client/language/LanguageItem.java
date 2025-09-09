package mod.slashblade.reforged.generated.client.language;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public class LanguageItem {
    @Getter
    final String key;

    @Getter
    Map<LanguageTypes, String> translations = new HashMap<>();

    public LanguageItem(String key) {
        this.key = key;
    }

    public LanguageItem addTranslation(LanguageTypes type, String translation) {
        translations.put(type, translation);
        return this;
    }

}
