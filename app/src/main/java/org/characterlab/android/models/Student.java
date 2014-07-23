package org.characterlab.android.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Students")
public class Student extends ParseObject implements Serializable{
    private static final String NAME_KEY = "Name";
    private static final String PROFILE_IMAGE_KEY = "ProfileImage";
    private static final String MAX_GROUPID__KEY = "Max_GroupId";

    public Student() {
        super();
    }

    public String getName() {
        return getString(NAME_KEY);
    }

    public void setName(String name) {
        put(NAME_KEY, name);
    }

    public ParseFile getProfileImage() {
        return getParseFile(PROFILE_IMAGE_KEY);
    }

    public int getMaxGroupId() {
        return getInt(MAX_GROUPID__KEY);
    }

    public void setMaxGroupId(int maxGroupId) {
        put(MAX_GROUPID__KEY, maxGroupId);
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", objId: " + getObjectId();
    }
}
