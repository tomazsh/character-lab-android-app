package org.characterlab.android.models;

import org.characterlab.android.R;

import java.io.Serializable;

public enum Strength implements Serializable {
    CURIOSITY (0, "Curiosity", R.raw.curiosity, R.raw.curiosity_circle, R.raw.curiosity_info, R.string.curisosity_card_description),
    GRATITUDE (1, "Gratitude", R.raw.gratitude, R.raw.gratitude_circle, R.raw.gratitude_info, R.string.gratitude_card_description),
    GRIT (2, "Grit", R.raw.grit, R.raw.grit_circle, R.raw.grit_info, R.string.grit_card_description),
    OPTIMISM (3, "Optimism", R.raw.optimism, R.raw.optimism_circle, R.raw.optimism_info, R.string.optimism_card_description),
    SELF_CONTROL (4, "Self Control", R.raw.self_control, R.raw.self_control_circle, R.raw.self_control_info, R.string.self_control_card_description),
    SOCIAL_INTELLIGENCE (5, "Social Intelligence", R.raw.social_intelligence, R.raw.social_intelligence_circle, R.raw.social_intelligence_info, R.string.social_intelligenced_card_description),
    ZEST (6, "Zest", R.raw.zest, R.raw.zest_circle, R.raw.zest_info, R.string.zest_card_description);

    private Strength(int index, String name, int iconId, int iconCircleId, int infoResourceId, int descriptionId) {
        this.index = index;
        this.name = name;
        this.iconId = iconId;
        this.iconCircleId = iconCircleId;
        this.infoResourceId = infoResourceId;
        this.descriptionId = descriptionId;
    }

    private int index;
    private String name;
    private int iconId;
    private int iconCircleId;
    private int infoResourceId;
    private int descriptionId;

    public int getIndex() { return index; }

    public String getName() {
        return name;
    }

    public int getIconId() {
        return iconId;
    }

    public int getIconCircleId() { return iconCircleId; }

    public int getInfoResourceId() {
        return infoResourceId;
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
