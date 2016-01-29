package com.android.shopping.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import com.android.shopping.R;

import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public class ShareUtils {

    public static final String ShareFBAPP = "com.facebook.katana";
    public static final String ShareTWAPP = "com.twitter.android";
    public static final String ShareWHAPP = "com.whatsapp";
    public static final String ShareIMAPP = "com.pinssible.padgram";
    public static final String ShareLIAPP = "jp.naver.line.android";

    public static final String ShareMsg = "Hey! I am inviting you to get a 500 Amazon gift card . " +
            " Visit--> http://www.g3kos.com/works/freegiftreward/ to get one for yourself. " +
            " Valid for first 5,000 people only!. Hurry!!";

    // 分享
    public static void shareTo(Context context, String pkgName, String msg) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            if (TextUtils.isEmpty(pkgName)) {
                Intent intent1 = Intent.createChooser(intent, context.getString(R.string.share));
                context.startActivity(intent1);
            } else {
                List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith(pkgName)) {
                        intent.setPackage(info.activityInfo.packageName);
                        break;
                    }
                }
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
