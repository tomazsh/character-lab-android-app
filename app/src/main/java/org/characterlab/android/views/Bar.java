package org.characterlab.android.views;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Region;

public class Bar {

    private int color;
    private String name;
    private float value;
    private float currentValue;
    private Path path;
    private Region region;

    public Bar() {
        currentValue = 0.0f;
    }

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;

        if (value < 3) {
            this.setColor(Color.parseColor("#FF4400"));
        } else if (value >= 3 && value <= 5) {
            this.setColor(Color.parseColor("#FFFF00"));
        } else {
            this.setColor(Color.parseColor("#44DD00"));
        }
    }
    public Path getPath() {
        return path;
    }
    public void setPath(Path path) {
        this.path = path;
    }
    public Region getRegion() {
        return region;
    }
    public void setRegion(Region region) {
        this.region = region;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }
}