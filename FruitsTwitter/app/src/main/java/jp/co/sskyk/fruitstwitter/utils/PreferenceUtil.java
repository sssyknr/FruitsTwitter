package jp.co.sskyk.fruitstwitter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * プリファレンスUtility
 */
public class PreferenceUtil {
    /** プリファレンス名 */
    private static final String PREF_NAME = "pref_fruitstwitter";

    public static void writeString(Context context, String key, String str) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, str);
        editor.apply();
    }

    public static void writeInt(Context context, String key, int integer) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, integer);
        editor.apply();
    }

    public static String readString(Context context, String key) {
        if (key == null) {
            return null;
        }
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    public static Integer readInt(Context context, String key) {
        if (key == null) {
            return -1;
        }
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }
}
