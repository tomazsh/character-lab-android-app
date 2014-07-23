package org.characterlab.android;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseObject;

import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CharacterLabApplication extends Application {
    private final static String PARSE_KEY = "LgdswefCyt4k4BpCHiR14YmyIFoElIlPmV5ZMHF7";
    private final static String PARSE_SECRET = "QwbQHMlHPxTPBrnC1bnpremQRUmvuugBNtVpo5yF";
    private static Context context;
    private static Map<String, ParseObject> parseObjectCache = new HashMap<String, ParseObject>();

    private static boolean actionBarBasedProgressBar = false;

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

    public static boolean isActionBarBasedProgressBar() {
        return actionBarBasedProgressBar;
    }

}
