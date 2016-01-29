package com.android.shopping.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shopping.R;

import java.util.ArrayList;
import java.util.List;

public class ShareDialog extends Dialog {
    private ImageView share_del;
    private GridView share_platform_gv;
    private TextView share_msg;
    private LayoutInflater mInflater;
    protected Context context;
    private List<PlatformInfo> mPlatformInfos;
    private View shareDialog;
    private String shareMsg = null;

    private static final String ShareFBAPP = "com.facebook.katana";
    private static final String ShareTWAPP = "com.twitter.android";
    private static final String ShareWHAPP = "com.whatsapp";
    private static final String ShareIMAPP = "com.pinssible.padgram";
    private static final String ShareLIAPP = "jp.naver.line.android";

    public class PlatformInfo {
        public int mInfoIV;
        public String mInfoTV;
        public String mAPP;

        public PlatformInfo(int mInfoIV, String mInfoTV, String mApp) {
            this.mInfoIV = mInfoIV;
            this.mInfoTV = mInfoTV;
            this.mAPP = mApp;
        }
    }

    public ShareDialog(Context context) {
        this(context, R.style.MyDialogStyle);
    }

    protected ShareDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        this(context, R.style.MyDialogStyle);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    @SuppressLint("InflateParams")
    public ShareDialog(final Context context, int theme) {
        super(context, theme);
        this.context = context;
        initPlatformInfo();
        shareDialog = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        setContentView(shareDialog, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        share_del = (ImageView) shareDialog.findViewById(R.id.share_del);
        share_del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        share_msg = (TextView) shareDialog.findViewById(R.id.share_msg);
        share_platform_gv = (GridView) shareDialog.findViewById(R.id.share_platform_gv);
        share_platform_gv.setAdapter(new PlatformGridAdapter(mPlatformInfos));
        share_platform_gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                PlatformInfo mInfo = mPlatformInfos.get(position);
                if (!isInstallApplication(context, mInfo.mAPP)) {
                    return;
                }
                shareTo(mInfo.mAPP);
            }
        });
    }

    public void setShareMsg(String msg) {
        this.shareMsg = msg;
        if (share_msg != null) {
            share_msg.setText(msg);
        }
    }

    // 初始化分享平台信息
    public void initPlatformInfo() {
        mPlatformInfos = new ArrayList<PlatformInfo>();
        if (isInstallApplication(context, ShareFBAPP)) {
            mPlatformInfos.add(new PlatformInfo(R.drawable.share_facebook, "Facebook", ShareFBAPP));
        } else {
            mPlatformInfos.add(new PlatformInfo(R.mipmap.share_facebook_pressed, "Facebook", ShareFBAPP));
        }

        if (isInstallApplication(context, ShareTWAPP)) {
            mPlatformInfos.add(new PlatformInfo(R.drawable.share_twitter, "Twitter", ShareTWAPP));
        } else {
            mPlatformInfos.add(new PlatformInfo(R.mipmap.share_twitter_pressed, "Twitter", ShareTWAPP));
        }

        if (isInstallApplication(context, ShareIMAPP)) {
            mPlatformInfos.add(new PlatformInfo(R.drawable.share_instagram, "Instagram", ShareIMAPP));
        } else {
            mPlatformInfos.add(new PlatformInfo(R.mipmap.share_instagram_pressed, "Instagram", ShareIMAPP));
        }

        if (isInstallApplication(context, ShareWHAPP)) {
            mPlatformInfos.add(new PlatformInfo(R.drawable.share_whatsapp, "Whatsapp", ShareWHAPP));
        } else {
            mPlatformInfos.add(new PlatformInfo(R.mipmap.share_whatsapp_pressed, "Whatsapp", ShareWHAPP));
        }

        if (isInstallApplication(context, ShareLIAPP)) {
            mPlatformInfos.add(new PlatformInfo(R.drawable.share_line, "Line", ShareLIAPP));
        } else {
            mPlatformInfos.add(new PlatformInfo(R.mipmap.share_line_pressed, "Line", ShareLIAPP));
        }

        mPlatformInfos.add(new PlatformInfo(R.drawable.share_more, context.getString(R.string.more), null));
    }

    // 检查是否安装该app
    private boolean isInstallApplication(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return true;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    // 分享
    private void shareTo(String packageName) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
            if (TextUtils.isEmpty(packageName)) {
                context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
            } else {
                List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith(packageName)) {
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


    private class PlatformGridAdapter extends BaseAdapter {
        private List<PlatformInfo> mPlatformInfos;

        public PlatformGridAdapter(List<PlatformInfo> mPlatformInfos) {
            mInflater = LayoutInflater.from(context);
            this.mPlatformInfos = mPlatformInfos;
        }

        @Override
        public int getCount() {
            return mPlatformInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return mPlatformInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            PlatformInfo mInfo = mPlatformInfos.get(position);
            if (convertView == null || convertView.getTag(R.drawable.ic_launcher) == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.platform_share_item, parent, false);
                holder.platformIV = (ImageView) convertView.findViewById(R.id.platform_item_iv);
                holder.platformTV = (TextView) convertView.findViewById(R.id.platform_item_tv);
                convertView.setTag(R.drawable.ic_launcher, holder);
            } else {
                holder = (ViewHolder) convertView.getTag(R.drawable.ic_launcher);
            }
            holder.platformIV.setImageResource(mInfo.mInfoIV);
            holder.platformTV.setText(mInfo.mInfoTV);
            return convertView;
        }
    }

    static class ViewHolder {
        ImageView platformIV;
        TextView platformTV;
    }
}
