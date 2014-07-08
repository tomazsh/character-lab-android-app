package org.characterlab.android;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseObject;

import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;

public class CharacterLabApplication extends Application {
    private final static String PARSE_KEY = "PFvi0rSsbsjGgqVajio2UUuxGfKv3VBjkx7j0Knt";
    private final static String PARSE_SECRET = "m4INIxSW8z7SgfY7Ic6Uiu6XlZcUIByAsjvpZj0J";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Student.class);
        CharacterLabApplication.context = this;

        ParseObject.registerSubclass(StrengthAssessment.class);
        ParseObject.registerSubclass(Student.class);
        Parse.initialize(this, PARSE_KEY, PARSE_SECRET);
    }
}
