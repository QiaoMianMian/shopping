package com.android.shopping;

import com.android.shopping.bean.GoodsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public class SubThread extends Thread {
    List<GoodsBean> mGoodsList;

    public SubThread(List<GoodsBean> mGoodsList) {
        this.mGoodsList = mGoodsList;
    }

    public void run() {
        while (true) {
            try {
                if (mGoodsList != null && (mGoodsList.size() > 0)) {
                    sleep(1000);
                    for (GoodsBean goodsBean : mGoodsList) {
                        if (goodsBean.getPeriod() > 0) {
                            goodsBean.setPeriod(goodsBean.getPeriod() - 1);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
