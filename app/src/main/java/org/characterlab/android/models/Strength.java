package org.characterlab.android.models;

import org.characterlab.android.R;

import java.util.HashMap;
import java.util.Map;

public enum Strength {
    CURIOSITY ("Curiosity", R.drawable.curiosity, R.string.curisosity_card_description),
    GRATITUDE ("Gratitude", R.drawable.gratitude, R.string.gratitude_card_description),
    GRIT ("Grit", R.drawable.grit, R.string.grit_card_description),
    OPTIMISM ("Optimism", R.drawable.optimism, R.string.optimism_card_description),
    SELF_CONTROL ("Self Control", R.drawable.self_control, R.string.self_control_card_description),
    SOCIAL_INTELLIGENCE ("Social Intelligence", R.drawable.social_intelligence, R.string.social_intelligenced_card_description),
    ZEST ("Zest", R.drawable.zest, R.string.zest_card_description);

    private Strength(String name, int iconId, int descriptionId) {
        this.name = name;
        this.iconId = iconId;
        this.descriptionId = descriptionId;
    }

    private String name;
    private int iconId;
    private int descriptionId;

    public String getName() {
        return name;
    }

    public int getIconId() {
        return iconId;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public static Strength fromName(String name) {
        for (Strength strength : Strength.values()) {
            if (strength.getName().equalsIgnoreCase(name)) {
                return strength;
            }
        }
        return null;
    }
}
