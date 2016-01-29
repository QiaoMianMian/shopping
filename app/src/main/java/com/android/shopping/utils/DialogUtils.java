package com.android.shopping.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.android.shopping.LoginActivity;
import com.android.shopping.R;
import com.android.shopping.bean.GoodsBean;
import com.android.shopping.db.MyDBHelper;
import com.android.shopping.widget.Dialog;

import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public class DialogUtils {

    public static void dialogLoginShow(final Context context) {
        String title = "";
        String message = "You haven't registered yet, please register first.";
        String yestxt = "Go";
        Dialog dialog = new Dialog(context, title, message, yestxt);
        dialog.setOnClickYes(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        dialog.show();
    }

    public static void dialogClaimedShow(Context context) {
        String title = "";
        String message = "You have claimed the prize!";
        Dialog dialog = new Dialog(context, title, message);
        dialog.show();
    }

    public static void dialogMissShow(Context context, String msg) {
        String title = "";
        Dialog dialog = new Dialog(context, title, msg);
        dialog.show();
    }

    public static void dialogShow(final Context context, final GoodsBean goodsBean) {
        String username = PreferenceUtils.getPrefString(context, PreferenceUtils.PRF_LOGIN_USER, "");
        if (TextUtils.isEmpty(username)) {
            dialogLoginShow(context);
        } else {
            if (goodsBean == null) {
                return;
            }
            if (goodsBean.isClaimed()) {
                if (goodsBean.period > 0) {
                    dialogClaimedShow(context);
                } else {
                    dialogMissShow(context, "Competition is over, no prize for you.");
                }
            } else {
                if (goodsBean.period > 0) {
                    final int spend = goodsBean.point;
                    String title = "";
                    String message = "";
                    final int points = PreferenceUtils.getPrefInt(context, PreferenceUtils.PRF_USER_POINTS, 0);
                    if (points >= spend) { //点数够用
                        message = "A total of " + points + " points, this will cost " + spend + " points.";
                        Dialog dialog = new Dialog(context, title, message, "YES");
                        dialog.setOnClickYes(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (spend > 0 && points >= spend) {
                                    goodsBean.setClaimed(true);
                                    TapjoyEx.spendTapjoyPoints(context, spend);
                                    DbUtils.insertGoods(context, goodsBean, MyDBHelper.TB_CLAIM);
                                    DbUtils.updateClaimed(context, goodsBean.code);
                                }
                            }
                        });
                        dialog.show();
                    } else {
                        message = "You need more credits to claim it.";
                        Dialog dialog = new Dialog(context, title, message, "EARN NOW");
                        dialog.setOnClickYes(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TapjoyEx.offerWallTapjoy(context);
                            }
                        });
                        dialog.show();
                    }
                } else {
                    dialogMissShow(context, "Competition is over.");
                }
            }
        }
    }

    public static void dialogShow(final Context context, final List<GoodsBean> goodsBeans, final int spend) {
        String title = "";
        String message = "";
        int points = PreferenceUtils.getPrefInt(context, PreferenceUtils.PRF_USER_POINTS, 0);
        if (points >= spend) { //点数够用
            message = "A total of " + points + " points, this will cost " + spend + " points.";
        } else {
            message = "You don't have enough points to earn points.";
        }
        Dialog dialog = new Dialog(context, title, message);
        dialog.setOnClickYes(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spend > 0) {
                    TapjoyEx.spendTapjoyPoints(context, spend);
                } else {
                    TapjoyEx.offerWallTapjoy(context);
                }
            }
        });
        dialog.setOnClickChange(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.show();
    }

    public static View createLoading(Context context) {
        View loading = LayoutInflater.from(context).inflate(R.layout.loading, null);
        return loading;
    }

}
