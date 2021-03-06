package org.characterlab.android.views;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Region;

import org.characterlab.android.R;

public class Bar {

    private int color;
    private int avgColor;
    private String name;
    private float value;
    private float currentValue;
    private float avgValue;
    private float currentAvgValue;
    private Path path;
    private Region region;

    public Bar() {
        currentValue = 0.0f;
        currentAvgValue = 0.0f;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAvgColor() {
        return avgColor;
    }

    public void setAvgColor(int avgColor) {
        this.avgColor = avgColor;
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

    public float getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(float avgValue) {
        this.avgValue = avgValue;
    }

    public float getCurrentAvgValue() {
        return currentAvgValue;
    }

    public void setCurrentAvgValue(float currentAvgValue) {
        this.currentAvgValue = currentAvgValue;
    }

}