package org.characterlab.android.models;

import android.content.Context;

import org.characterlab.android.CharacterLabApplication;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name="item")
public class StrengthInfoItem implements Serializable {

    public enum Type {
        TEXT,
        VIDEO
    }

    @Attribute(name="type")
    private String mType;

    @Element(name="title")
    private String mTitle;

    @Element(name="content", required=false, data=true)
    private String mContents;

    @Element(name="cover", required=false)
    private String mCover;

    @Element(name="link", required=false)
    private String mLink;

    public Type getType() {
        return Type.valueOf(mType.toUpperCase());
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContents() {
        return mContents;
    }

    public String getLink() {
        return mLink;
    }

    public int getCoverResourceId(Context c) {
        if (mCover == null) {
            return 0;
        } else {
            return c.getResources().getIdentifier(mCover, "raw", c.getPackageName());
        }
    }
}
