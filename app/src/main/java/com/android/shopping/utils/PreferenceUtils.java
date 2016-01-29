package com.android.shopping.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {
    public static String PRF_FIRST_LOGIN = "prf.first.login";
    public static String PRF_LOGIN_USER = "prf.login.user"; //用户账号
    public static String PRF_LOGIN_PWD = "prf.login.pwd";//用户密码
    public static String PRF_LOGIN_EXIT = "prf.login.exit";
    public static String PRF_REMEBER_PWD = "prf.remeber.pwd";//记住密码
    public static String PRF_AVATAR_URI = "prf.avatar.uri";//头像
    public static String PRF_USER_POINTS = "prf.user.points";//积分
    public static String PRF_FAB_GUIDE = "prf.fab.guide";//引导按钮
    public static String PRF_USER_ADDRESS = "prf.user.address";//用户地址
    public static String PRF_USER_PHONE = "prf.user.phone";//用户电话
    public static String PRF_USER_GENDER = "prf.user.gender";//性别

    public static void init(String key) {
        sSharedPreferencesKey = key;
    }

    public static String sSharedPreferencesKey = "com.android.shopping.prf";

    public static String getPrefString(Context context, String key, final String defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, final String key, final String value) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        save(settings.edit().putString(key, value));
    }

    private static void save(Editor editor) {
        if (editor == null)
            return;
        // editor.apply();//异步
        editor.commit();// 同步
    }

    public static boolean getPrefBoolean(Context context, final String key,
                                         final boolean defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        boolean b = defaultValue;
        try {
            int i = settings.getInt(key, 0);
            if (i == 1)
                b = true;
            else if (i == -1)
                b = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean hasKey(Context context, final String key) {
        return context.getSharedPreferences(sSharedPreferencesKey, Context.MODE_MULTI_PROCESS)
                .contains(key);
    }

    public static void setPrefBoolean(Context context, final String key, final boolean value) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        try {
            if (value) {
                save(settings.edit().putInt(key, 1));
            } else {
                save(settings.edit().putInt(key, -1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPrefInt(Context context, final String key, final int value) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        save(settings.edit().putInt(key, value));
    }

    public static int getPrefInt(Context context, final String key, final int defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefFloat(Context context, final String key, final float value) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        save(settings.edit().putFloat(key, value));
    }

    public static float getPrefFloat(Context context, final String key, final float defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        return settings.getFloat(key, defaultValue);
    }

    public static void setPrefLong(Context context, final String key, final long value) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        save(settings.edit().putLong(key, value));
    }

    public static long getPrefLong(Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(sSharedPreferencesKey,
                Context.MODE_MULTI_PROCESS);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(Context context, final SharedPreferences p) {
        final Editor editor = p.edit();
        editor.clear();
        save(editor);
    }
}
