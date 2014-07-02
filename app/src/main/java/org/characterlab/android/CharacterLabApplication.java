package org.characterlab.android;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseObject;

import org.characterlab.android.models.Student;

public class CharacterLabApplication extends Application {
    private final static String PARSE_KEY = "QeL1qkcLeNT0DPzSBIycFhmQE1Bj9ahY1ApGfFVO";
    private final static String PARSE_SECRET = "3rdP8DYhbNDOvug5mKVggRxm9wJpMaKgQVoxLljn";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Student.class);
        CharacterLabApplication.context = this;
        Parse.initialize(this, PARSE_KEY, PARSE_SECRET);
    }
}
