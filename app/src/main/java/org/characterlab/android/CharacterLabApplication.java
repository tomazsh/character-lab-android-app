package org.characterlab.android;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseUser;

public class CharacterLabApplication extends Application {
    private final static String PARSE_KEY = "QeL1qkcLeNT0DPzSBIycFhmQE1Bj9ahY1ApGfFVO";
    private final static String PARSE_SECRET = "3rdP8DYhbNDOvug5mKVggRxm9wJpMaKgQVoxLljn";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        CharacterLabApplication.context = this;
        Parse.initialize(this, PARSE_KEY, PARSE_SECRET);
    }
}
