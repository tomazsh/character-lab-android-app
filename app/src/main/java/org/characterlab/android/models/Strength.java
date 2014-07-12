package org.characterlab.android.models;

import org.characterlab.android.R;

public enum Strength {
    CURIOSITY (0, "Curiosity", R.raw.curiosity, R.raw.curiosity_circle, R.string.curisosity_card_description),
    GRATITUDE (1, "Gratitude", R.raw.gratitude, R.raw.gratitude_circle, R.string.gratitude_card_description),
    GRIT (2, "Grit", R.raw.grit, R.raw.grit_circle, R.string.grit_card_description),
    OPTIMISM (3, "Optimism", R.raw.optimism, R.raw.optimism_circle, R.string.optimism_card_description),
    SELF_CONTROL (4, "Self Control", R.raw.self_control, R.raw.self_control_circle, R.string.self_control_card_description),
    SOCIAL_INTELLIGENCE (5, "Social Intelligence", R.raw.social_intelligence, R.raw.social_intelligence_circle, R.string.social_intelligenced_card_description),
    ZEST (6, "Zest", R.raw.zest, R.raw.zest_circle, R.string.zest_card_description);

    private Strength(int index, String name, int iconId, int iconCircleId, int descriptionId) {
        this.index = index;
        this.name = name;
        this.iconId = iconId;
        this.iconCircleId = iconCircleId;
        this.descriptionId = descriptionId;
    }

    private int index;
    private String name;
    private int iconId;
    private int iconCircleId;
    private int descriptionId;

    public int getIndex() { return index; }
    public String getName() {
        return name;
    }

    public int getIconId() {
        return iconId;
    }

    public int getIconCircleId() { return iconCircleId; }

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
