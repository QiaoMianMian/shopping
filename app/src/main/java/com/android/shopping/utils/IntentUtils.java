package com.android.shopping.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/1/7.
 */
public class IntentUtils {
    public static final String EXTRA_PHOTO_URL = "photo_url";

    public static void sendBroadCast(Context context, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        context.sendBroadcast(intent);
    }
}
