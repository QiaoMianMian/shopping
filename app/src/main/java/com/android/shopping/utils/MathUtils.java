package com.android.shopping.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/29.
 */
public class MathUtils {

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String getTimeFormat(long time) {
        String H;
        String M;
        String S;
        long s = time % 60;
        long m = time / 60 % 60;
        long h = time / 60 / 60;
        if (h == 0) {
            H = "00";
        } else if (h > 0 && h < 10) {
            H = "0" + h;
        } else {
            H = "" + h;
        }
        if (m == 0) {
            M = "00";
        } else if (m > 0 && m < 10) {
            M = "0" + m;
        } else {
            M = "" + m;
        }
        if (s == 0) {
            S = "00";
        } else if (s > 0 && s < 10) {
            S = "0" + s;
        } else {
            S = "" + s;
        }
        return H + ":" + M + ":" + S;
    }

    public static String getSimpleDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        date = Calendar.getInstance().getTime();
        return format.format(date);
    }

    public static String getSimpleDate1(Long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }
}
