package org.characterlab.android.models;

import android.content.Context;

import org.characterlab.android.R;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Root
public class StrengthInfo implements Serializable {
    private Strength mStrength;

    @Element(name="name")
    private String mName;

    @Element(name="icon_image")
    private String mIconImageId;

    @Element(name="hero_image")
    private String mHeroImageId;

    @Element(name="description", required=false)
    private String mDescription;

    @Element(name="about_title", required=false)
    private String mAboutTitle;

    @ElementList(name="about_items", required=false)
    private List<StrengthInfoItem> mAboutItems;

    @Element(name="build_title", required=false)
    private String mBuildTitle;

    @ElementList(name="build_items", required=false)
    private List<StrengthInfoItem> mBuildItems;

    @Element(name="assessment_questions", required=false)
    private String mAssessmentQuestions;

    public static StrengthInfo fromStrength(Context context, Strength strength) {
        Serializer serializer = new Persister();
        InputStream stream = context.getResources().openRawResource(strength.getInfoResourceId());

        StrengthInfo strengthInfo = null;
        try {
            strengthInfo = serializer.read(StrengthInfo.class, stream);
            strengthInfo.setStrength(strength);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strengthInfo;
    }

    public Strength getStrength() {
        return mStrength;
    }

    private void setStrength(Strength strength) {
        mStrength = strength;
    }

    public String getName() {
        return mName;
    }

    public int getIconImageResource(Context c) {
        if (mIconImageId == null) {
            return 0;
        } else {
            return c.getResources().getIdentifier(mIconImageId, "raw", c.getPackageName());
        }
    }

    public int getHeroImageResourceId(Context c) {
        if (mHeroImageId == null) {
            return 0;
        } else {
            return c.getResources().getIdentifier(mHeroImageId, "raw", c.getPackageName());
        }
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAboutTitle() { return mAboutTitle; }

    public List<StrengthInfoItem> getAboutItems() {
        return mAboutItems;
    }

    public String getBuildTitle() { return mBuildTitle; }

    public List<StrengthInfoItem> getBuildItems() {
        return mBuildItems;
    }

    public String getAssessmentQuestions() { return mAssessmentQuestions; }
}
