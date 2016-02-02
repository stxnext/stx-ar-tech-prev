package com.stx.ar.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Łukasz Ciupa on 22.01.2016.
 */
public class Preferences {

    private static Preferences instance = null;

    private static final String PREFERENCES_NAME = "com.stx.ar";
    private Context context = null;
    private SharedPreferences preferences = null;
    private static final String IS_TUTORIAL_DONE = "isTutorialDone";

    private Preferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static Preferences getInstance(Context context) {
        if (instance == null) {
            instance = new Preferences(context);
        }
        return instance;
    }

    public boolean isTutorialDone() {
        return preferences.getBoolean(IS_TUTORIAL_DONE, false);
    }

    public void setTutorialDone(boolean isTutorialDone) {
        preferences.edit().putBoolean(IS_TUTORIAL_DONE, isTutorialDone).commit();
    }

}
