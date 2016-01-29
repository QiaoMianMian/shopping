package com.android.shopping.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

public class StatisticsUtils {
    public static final String SS_FRG_HOME = "ss_frg_home"; //在HOME界面
    public static final String SS_HOME_CLICK_HEADER = "ss_home_click_header"; //HOME界面中头部点击
    public static final String SS_HOME_CLICK_ITEM = "ss_home_click_item"; //HOME界面中商品点击
    public static final String SS_FRG_TASK = "ss_frg_task"; //在TASK界面
    public static final String SS_TASK_SHAREFB = "ss_task_share_fb"; //Facebook分享按钮
    public static final String SS_TASK_SHAREWP = "ss_task_share_wp"; //Whatsapp分享按钮
    public static final String SS_TASK_SHAREMR = "ss_task_share_mr"; //更多分享按钮
    public static final String SS_TASK_ITEM = "ss_task_item"; //在Task界面Item
    public static final String SS_FRG_LIST = "ss_frg_list"; //在LIST界面
    public static final String SS_LIST_CLICK_EDIT = "ss_list_click_edit";//LIST界面编辑按钮
    public static final String SS_LIST_CLICK_DEL = "ss_list_click_del";//LIST界面商品删除按钮
    public static final String SS_LIST_CLICK_PLUS = "ss_list_click_plus";//LIST界面增加点数按钮
    public static final String SS_LIST_CLICK_SUB = "ss_list_click_sub";//LIST界面减少点数按钮
    public static final String SS_LIST_CLICK_ACCOUNT = "ss_list_click_account";//LIST界面结账按钮
    public static final String SS_LIST_CLICK_ALLDEL = "ss_list_click_alldel";//LIST界面全选删除按钮
    public static final String SS_LIST_CLICK_DELETE = "ss_list_click_delete";//LIST界面删除所有按钮
    public static final String SS_LIST_CLICK_EMPTY = "ss_list_click_empty";//LIST界面没有商品按钮
    public static final String SS_LIST_CLICK_ICON = "ss_list_click_icon";//LIST界面图标(跳转链接)
    public static final String SS_LIST_CLICK_FREE = "ss_list_click_free";//获取免费点数
    public static final String SS_FRG_PERSON = "ss_frg_person"; //在PERSON界面
    public static final String SS_PERSON_AVATAR = "ss_person_avatar"; //PERSON界面AVATAR
    public static final String SS_PERSON_TOP = "ss_person_top"; //PERSON界面TOP
    public static final String SS_PERSON_LOGIN = "ss_person_login";//点击Login按钮
    public static final String SS_PERSON_RECORD = "ss_person_record"; //PERSON界面RECORD
    public static final String SS_PERSON_ADDRESS = "ss_person_address"; //PERSON界面ADDRESS
    public static final String SS_PERSON_POINTS = "ss_person_points"; //PERSON界面POINTS
    public static final String SS_PERSON_ABOUT = "ss_person_about"; //PERSON界面ABOUT
    public static final String SS_PERSON_EXIT = "ss_person_exit"; //PERSON界面EXIT
    public static final String SS_TAPJOY_SUCCESS = "ss_tapjoy_success"; //连接TAPJOY成功
    public static final String SS_TAPJOY_FAILURE = "ss_tapjoy_failure"; //连接TAPJOY失败
    public static final String SS_TJ_GET_P_SUCCESS = "ss_tapjoy_get_p_success"; //获取TAPJOY积分成功
    public static final String SS_TJ_GET_P_FAILURE = "ss_tapjoy_get_p_failure"; //获取TAPJOY积分失败
    public static final String SS_TJ_SPEND_SUCCESS = "ss_tapjoy_spend_success"; //花费TAPJOY积分成功
    public static final String SS_TJ_SPEND_FAILURE = "ss_tapjoy_spend_failure"; //花费TAPJOY积分失败
    public static final String SS_TJ_WALL_SUCCESS = "ss_tj_wall_success"; //获取TAPJOY应用墙成功
    public static final String SS_TJ_WALL_FAILURE = "ss_tj_wall_failure"; //获取TAPJOY应用墙失败
    public static final String SS_TJ_WALL_READY = "ss_tj_wall_ready"; //TAPJOY应用墙Ready
    public static final String SS_TJ_WALL_SHOW = "ss_tj_wall_show"; //TAPJOY应用墙Show
    public static final String SS_TJ_WALL_DISMISS = "ss_tj_wall_dismiss"; //TAPJOY Dismiss
    public static final String SS_TJ_WALL_P_REQUEST = "ss_tj_wall_p_request"; //TAPJOY PurchaseRequest
    public static final String SS_TJ_WALL_R_REQUEST = "ss_tapjoy_r_request"; //TAPJOY RewardRequest
    public static final String SS_MAIN_FAB = "ss_main_fab";//引导按钮
    public static final String SS_TRY_NOW = "ss_try_now";//体验
    public static final String SS_REGISTER = "ss_register";//注册
    public static final String SS_LOGNI = "ss_login";//登录
    public static final String SS_REMEBER = "ss_remeber";//记住密码
    public static final String SS_CLAIM_IT = "ss_claim_it";//点击claim
    public static final String SS_DETAIL_GOODS = "ss_detail_goods";//商品详情界面
    public static final String SS_DETAIL_CLAIM = "ss_detail_claim";//商品详情界面Claim按钮

    public static void onResume(Activity activity) {
        MobclickAgent.onResume(activity);
        TCAgent.onResume(activity);
    }

    public static void onPause(Activity activity) {
        MobclickAgent.onPause(activity);
        TCAgent.onPause(activity);
    }

    public static void event(Context context, String id, String... args) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("IMEI", SystemUtils.getKeyImei(context));
        if (args != null && args.length % 2 == 0) {
            for (int i = 0; i < args.length; i = i + 2) {
                map.put(args[i], args[i + 1]);
            }
        }
        MobclickAgent.onEvent(context, id, map);
        TCAgent.onEvent(context, id, id, map);
    }
}
