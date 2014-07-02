package org.characterlab.android.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Students")
public class Student extends ParseObject implements Serializable {
    private static final String NAME_KEY = "Name";

    public Student() {
       super("Students");
    }

    public String getName() {
        return getString(NAME_KEY);
    }

    public void setName(String name) {
        put(NAME_KEY, name);
    }
}
