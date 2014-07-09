package org.characterlab.android.models;

public enum Strength {
    CURIOSITY ("Curiosity"),
    GRATITUDE ("Gratitude"),
    GRIT ("Grit"),
    OPTIMISM ("Optimism"),
    SELF_CONTROL ("Self Control"),
    SOCIAL_INTELLIGENCE ("Social Intelligence"),
    ZEST ("Zest");

    private Strength(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
