package com.android.shopping.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shopping.R;


public class ToastUtils {
    private static Toast msgToast;


    public static void show(Context context, int resId) {
        if (resId > 0) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(Context context, int resId) {
        if (resId > 0) {
            String msg = context.getResources().getString(resId);
            showToast(context, msg);
        }
    }

    public static void showToast(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            View localView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            ((TextView) localView.findViewById(R.id.toast_msg)).setText(msg);
            if (msgToast == null) {
                msgToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            }
            msgToast.setView(localView);
            msgToast.setGravity(17, 0, 0);
            msgToast.show();
        }
    }
}
