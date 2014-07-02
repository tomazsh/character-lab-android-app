package org.characterlab.android.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Strengths")
public class Strength extends ParseObject implements Serializable {
    private static final String NAME_KEY = "Name";
    private static final String DESCRIPTION_KEY = "Description";

    public Strength() {
        super();
    }

    public String getName() {
        return getString(NAME_KEY);
    }

    public void setName(String name) {
        put(NAME_KEY, name);
    }

    public String getDescription() {
        return getString(DESCRIPTION_KEY);
    }

    public void setDescription(String description) {
        put(DESCRIPTION_KEY, description);
    }
}
