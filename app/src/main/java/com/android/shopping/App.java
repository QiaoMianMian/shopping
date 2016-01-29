package com.android.shopping;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.android.shopping.bean.GoodsBean;
import com.android.shopping.db.MyDBHelper;
import com.android.shopping.utils.AppUtils;
import com.android.shopping.utils.DbUtils;
import com.android.shopping.utils.Logger;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/28.
 */
public class App extends Application {
    public static List<GoodsBean> goods = new ArrayList<GoodsBean>();
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        String UMENG_APPKEY = AppUtils.getMeta(this, "UMENG_APPKEY");
        String TD_APP_ID = AppUtils.getMeta(this, "TD_APP_ID");
        String TD_CHANNEL_ID = AppUtils.getMeta(this, "UMENG_CHANNEL");
        Log.d("YWS", "UMENG_APPKEY:" + UMENG_APPKEY + ",TD_APP_ID:" + TD_APP_ID + ",TD_CHANNEL_ID:" + TD_CHANNEL_ID);
        if (TextUtils.isEmpty(TD_APP_ID) || TextUtils.isEmpty(TD_CHANNEL_ID)) {
            TCAgent.init(this, TD_APP_ID, TD_CHANNEL_ID);
        } else {
            TCAgent.init(this);
        }
        mImageLoader = ImageLoader.getInstance();
        initImageLoader(this);
        MyDBHelper.create(this);
        initDatas();
//        new SubThread(goods).start();
    }

    public void initDatas() {
        List<GoodsBean> mClaimedGoods = DbUtils.queryCliamGoods(this);
        goods.addAll(mClaimedGoods);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();

        ImageLoader.getInstance().init(config.build());
    }
}
