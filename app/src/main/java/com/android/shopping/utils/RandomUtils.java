package com.android.shopping.utils;

import android.widget.ImageView;

import com.android.shopping.R;
import com.android.shopping.bean.DataSet;

import java.util.Random;

/**
 * Created by Administrator on 2015/12/30.
 */
public class RandomUtils {

    public static boolean random() {
        int random = new Random().nextInt(10);
        if (random > 3) {
            return true;
        }
        return false;
    }

    public static int random(int i) {
        int random = new Random().nextInt(i);
        return random;
    }

    public static String getCommentTime() {
        long current = System.currentTimeMillis();
        int random = random(2 * 24 * 60 * 60 * 1000);
        long time = current - (long) random;
        return MathUtils.getSimpleDate1(time);
    }

    public static String getLuckerName() {
        String luckername = "--";
        DataSet dataSet = new DataSet();
        String[] LuckerNames = dataSet.getLuckyNames();
        int length = LuckerNames.length;
        int random = new Random().nextInt(length);
        luckername = LuckerNames[random];
        return luckername.toUpperCase();
    }

    public static String getLuckerTime() {
        return "" + (new Random().nextInt(10) + 1);
    }

    public static String getLuckerGoods() {
        String luckergood = "--";
        DataSet dataSet = new DataSet();
        String[] LuckerGoods = dataSet.getGoodsNames();
        int length = LuckerGoods.length;
        int random = new Random().nextInt(length);
        luckergood = LuckerGoods[random];
        return luckergood;
    }

    public static void setRatingStars(int score, ImageView star1, ImageView star2, ImageView star3,
                                      ImageView star4, ImageView star5) {
        if (score == 1) {
            star1.setImageResource(R.mipmap.ic_star_half);
        } else if (score > 1) {
            star1.setImageResource(R.mipmap.ic_star_on);
        }
        if (score == 3) {
            star2.setImageResource(R.mipmap.ic_star_half);
        } else if (score > 3) {
            star2.setImageResource(R.mipmap.ic_star_on);
        }
        if (score == 5) {
            star3.setImageResource(R.mipmap.ic_star_half);
        } else if (score > 5) {
            star3.setImageResource(R.mipmap.ic_star_on);
        }
        if (score == 7) {
            star4.setImageResource(R.mipmap.ic_star_half);
        } else if (score > 7) {
            star4.setImageResource(R.mipmap.ic_star_on);
        }
        if (score == 9) {
            star5.setImageResource(R.mipmap.ic_star_half);
        } else if (score > 9) {
            star5.setImageResource(R.mipmap.ic_star_on);
        }
    }
}
