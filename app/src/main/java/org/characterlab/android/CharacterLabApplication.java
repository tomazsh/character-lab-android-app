package org.characterlab.android;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseObject;

import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;

import java.util.HashMap;
import java.util.Map;

public class CharacterLabApplication extends Application {
    private final static String PARSE_KEY = "R13BjxIo1x17hjo5NpVbFiuPT0kmZhYSXEMsrdto";
    private final static String PARSE_SECRET = "7jzw4ACagzUX7q8agcvoUKxTLediFnET1FzqLnGG";
    private static Context context;
    private static Map<String, ParseObject> parseObjectCache = new HashMap<String, ParseObject>();

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Student.class);
        CharacterLabApplication.context = this;

        ParseObject.registerSubclass(StrengthAssessment.class);
        ParseObject.registerSubclass(Student.class);
        Parse.initialize(this, PARSE_KEY, PARSE_SECRET);
    }


    //TODO: Replace with Parse Local Datastore pattern
    public static void putInCache(String objectId, ParseObject parseObject) {
        parseObjectCache.put(objectId, parseObject);
    }

    public static ParseObject readFromCache(String objectId) {
        return parseObjectCache.get(objectId);
    }


}
