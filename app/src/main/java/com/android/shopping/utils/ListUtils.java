package com.android.shopping.utils;

import com.android.shopping.bean.GoodsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/4.
 */
public class ListUtils {

    public static void removeRepeat(List<GoodsBean> beans) {
        for (int i = 0; i < beans.size() - 1; i++) {
            for (int j = beans.size() - 1; j > i; j--) {
                if (beans.get(j).code.equals(beans.get(i).code)) {
                    beans.remove(j);
                }
            }
        }
    }

    public static void removeRepeatPre(List<GoodsBean> beans) {
        for (int i = 0; i < beans.size() - 1; i++) {
            for (int j = beans.size() - 1; j > i; j--) {
                if (beans.get(j).code.equals(beans.get(i).code)) {
                    beans.remove(i);
                }
            }
        }
    }
}
