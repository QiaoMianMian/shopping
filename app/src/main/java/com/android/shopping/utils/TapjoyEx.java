package com.android.shopping.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.shopping.Contants;
import com.tapjoy.TJActionRequest;
import com.tapjoy.TJAwardCurrencyListener;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJEarnedCurrencyListener;
import com.tapjoy.TJError;
import com.tapjoy.TJGetCurrencyBalanceListener;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJSpendCurrencyListener;
import com.tapjoy.TJVideoListener;
import com.tapjoy.Tapjoy;
import com.tapjoy.TapjoyConnectFlag;

import java.util.Hashtable;

/**
 * Created by Administrator on 2015/12/28.
 */
public class TapjoyEx {
    private static TJPlacement video;
    public static String tapjoySdkId;
    public static String tapjoySdkKey;

    static {
//DEBUG
//        tapjoySdkId = "34027022155";
//        tapjoySdkKey = "u6SfEbh_TA-WMiGqgQ3W8QECyiQIURFEeKm0zbOggubusy-o5ZfXp33sTXaD";
// funny app
//            tapjoySdkId = "f6df00aa-0ddc-491d-bf05-e90f0389278b";
//            tapjoySdkKey = "9t8Aqg3cSR2_BekPA4kniwECWC02pSzLkLkZSIGfs2E8XFZ4rY3UKa0N3UqD";
//Free Gift Reward
        tapjoySdkId = "c078ff43-5f65-450e-bee0-56cd9d9350b3";
        tapjoySdkKey = "wHj_Q19lRQ6-4FbNnZNQswECIbROvpB0AkgjOicsHe44DNS_GFZ-JE_HEpjt";
    }

    //链接Tapjoy后台
    public static void connectTapjoy(final Context context) {
        final long start = System.currentTimeMillis();
        Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
        connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
        Log.i("YWS", "tapjoySdkId:" + tapjoySdkId + ",tapjoySdkKey:" + tapjoySdkKey);
        Tapjoy.setGcmSender(tapjoySdkId);
        Tapjoy.connect(context, tapjoySdkKey, connectFlags, new TJConnectListener() {

            @Override
            public void onConnectSuccess() {
                Logger.d("connect:success");
                Tapjoy.setEarnedCurrencyListener(new TJEarnedCurrencyListener() {
                    @Override
                    public void onEarnedCurrency(String name, int amount) {
                        Logger.d("earned: name:" + name + ",amount:" + amount);
                    }
                });
                getTapjoyPoints(context);
//                videoTapjop(context);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TAPJOY_SUCCESS, "peroid", "" + (end - start));
            }

            @Override
            public void onConnectFailure() {
                Logger.e("connect:failure");
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TAPJOY_FAILURE, "peroid", "" + (end - start));
            }
        });
    }

    //获取用户的积分总数
    public static void getTapjoyPoints(final Context context) {
        final long start = System.currentTimeMillis();
        Tapjoy.getCurrencyBalance(new TJGetCurrencyBalanceListener() {
            @Override
            public void onGetCurrencyBalanceResponse(String s, int i) {
                Logger.d("Get:s=" + s + ",points=" + i);
                PreferenceUtils.setPrefInt(context, PreferenceUtils.PRF_USER_POINTS, i);
                context.sendBroadcast(new Intent(Contants.BR_GET_POINTS));
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_GET_P_SUCCESS, "peroid", "" + (end - start), "name", s, "points", "" + i);
            }

            @Override
            public void onGetCurrencyBalanceResponseFailure(String s) {
                Logger.e("Get:" + s);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_GET_P_FAILURE, "peroid", "" + (end - start));
            }
        });
    }

    //用户消费的积分数
    public static void spendTapjoyPoints(final Context context, final int amount) {
        final long start = System.currentTimeMillis();
        IntentUtils.sendBroadCast(context, Contants.BR_LOADING_SHOW);
        Tapjoy.spendCurrency(amount, new TJSpendCurrencyListener() {
            @Override
            public void onSpendCurrencyResponse(String name, int balance) {
                Logger.d("Spend:name=" + name + ",balance=" + balance);
                IntentUtils.sendBroadCast(context, Contants.BR_SPEND_SUCCESS);
                getTapjoyPoints(context);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_SPEND_SUCCESS, "peroid", "" + (end - start),
                        "name", name, "points", "" + balance, "amount", "" + amount);
            }

            @Override
            public void onSpendCurrencyResponseFailure(String error) {
                Logger.e("Spend:" + error);
                IntentUtils.sendBroadCast(context, Contants.BR_SPEND_FAILED);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_SPEND_FAILURE, "peroid", "" + (end - start));
            }
        });
    }

    //用户赚得的积分数
    public static void awardTapjoyPoints(int amount) {
        Tapjoy.awardCurrency(amount, new TJAwardCurrencyListener() {
            @Override
            public void onAwardCurrencyResponse(String name, int balance) {
                Logger.d("Award:name=" + name + ",balance=" + balance);
            }

            @Override
            public void onAwardCurrencyResponseFailure(String error) {
                Logger.e("Award:" + error);
            }
        });
    }

    //加载Tapjoy应用墙
    public static void offerWallTapjoy(final Context context) {
        final long start = System.currentTimeMillis();
        IntentUtils.sendBroadCast(context, Contants.BR_LOADING_SHOW);
        TJPlacement offerwall = new TJPlacement(context, "AppLaunch", new TJPlacementListener() {
            @Override
            public void onRequestSuccess(TJPlacement tjPlacement) {
                Logger.d("offerwall:onRequestSuccess");
                IntentUtils.sendBroadCast(context, Contants.BR_LOADING_HIDE);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_WALL_SUCCESS, "peroid", "" + (end - start));
            }

            @Override
            public void onRequestFailure(TJPlacement tjPlacement, TJError tjError) {
                Logger.e("offerwall:onRequestFailure");
                IntentUtils.sendBroadCast(context, Contants.BR_LOADING_HIDE);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_WALL_FAILURE, "peroid", "" + (end - start));
            }

            @Override
            public void onContentReady(TJPlacement tjPlacement) {
                Logger.d("offerwall:onContentReady");
                IntentUtils.sendBroadCast(context, Contants.BR_LOADING_HIDE);
                tjPlacement.showContent();
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_WALL_READY, "peroid", "" + (end - start));
            }

            @Override
            public void onContentShow(TJPlacement tjPlacement) {
                Logger.d("offerwall:onContentShow");
                IntentUtils.sendBroadCast(context, Contants.BR_LOADING_HIDE);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_WALL_SHOW, "peroid", "" + (end - start));
            }

            @Override
            public void onContentDismiss(TJPlacement tjPlacement) {
                Logger.d("offerwall:onContentDismiss");
                TapjoyEx.getTapjoyPoints(context);
                IntentUtils.sendBroadCast(context, Contants.BR_LOADING_HIDE);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_WALL_DISMISS, "peroid", "" + (end - start));
            }

            @Override
            public void onPurchaseRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s) {
                Logger.d("offerwall:onPurchaseRequest");
                IntentUtils.sendBroadCast(context, Contants.BR_LOADING_HIDE);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_WALL_P_REQUEST, "peroid", "" + (end - start));
            }

            @Override
            public void onRewardRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s, int i) {
                Logger.d("offerwall:onRewardRequest");
                IntentUtils.sendBroadCast(context, Contants.BR_LOADING_HIDE);
                long end = System.currentTimeMillis();
                StatisticsUtils.event(context, StatisticsUtils.SS_TJ_WALL_R_REQUEST, "peroid", "" + (end - start));
            }
        });
        offerwall.requestContent();
    }

    //加载Tapjoy视频
    public static void videoTapjop(final Context context) {
        video = new TJPlacement(context, "AppLaunch", new TJPlacementListener() {

            @Override
            public void onRequestSuccess(TJPlacement tjPlacement) {
                Logger.d("video:onRequestSuccess");
            }

            @Override
            public void onRequestFailure(TJPlacement tjPlacement, TJError tjError) {
                Logger.e("video:onRequestFailure");
            }

            @Override
            public void onContentReady(TJPlacement tjPlacement) {
                Logger.d("video:onContentReady");
                tjPlacement.showContent();
            }

            @Override
            public void onContentShow(TJPlacement tjPlacement) {
                Logger.d("video:onContentShow");
            }

            @Override
            public void onContentDismiss(TJPlacement tjPlacement) {
                Logger.d("video:onContentDismiss");
                getTapjoyPoints(context);
                videoTapjop(context);
            }

            @Override
            public void onPurchaseRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s) {
                Logger.d("video:onPurchaseRequest");
            }

            @Override
            public void onRewardRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s, int i) {
                Logger.d("video:onRewardRequest");
            }
        });
        video.requestContent();
        Tapjoy.setVideoListener(new TJVideoListener() {
            @Override
            public void onVideoStart() {
                Logger.d("video:onVideoStart");
            }

            @Override
            public void onVideoError(int i) {
                Logger.e("video:onVideoError");
            }

            @Override
            public void onVideoComplete() {
                Logger.d("video:onVideoComplete");
                getTapjoyPoints(context);
            }
        });
    }

    //播放Tapjoy视频
    public static void playVideoTapjoy() {
        if (video != null && video.isContentAvailable()) {
            if (video.isContentReady()) {
                video.showContent();
            } else {
                Logger.e("video not content ready!");
            }
        } else {
            Logger.e("video not content available!");
        }
    }
}
