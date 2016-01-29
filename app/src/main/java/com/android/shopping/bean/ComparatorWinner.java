package com.android.shopping.bean;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/1/20.
 */
public class ComparatorWinner implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        WinnerBean winnerBean0 = (WinnerBean) lhs;
        WinnerBean winnerBean1 = (WinnerBean) rhs;
        int flag = winnerBean1.time.compareTo(winnerBean0.time);
        if (flag == 0) {
            return winnerBean1.name.compareTo(winnerBean0.name);
        } else {
            return flag;
        }
    }
}
