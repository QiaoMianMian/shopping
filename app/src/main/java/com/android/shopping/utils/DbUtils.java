package com.android.shopping.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.android.shopping.bean.GoodsBean;
import com.android.shopping.db.DatabaseException;
import com.android.shopping.db.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/9.
 */
public class DbUtils {

    //商品插入TB_GOODS
    public static void insertDbGoods(Context context, List<GoodsBean> list, String dbName) {
        if (context == null || list == null || list.size() == 0) {
            return;
        }
        for (GoodsBean goodsBean : list) {
            ContentValues cv = new ContentValues();
            cv.put("id", goodsBean.id);
            cv.put("code", goodsBean.code);
            cv.put("name", goodsBean.name);
            cv.put("category", goodsBean.category);
            cv.put("price", goodsBean.price);
            cv.put("desc", goodsBean.desc);
            cv.put("resid", goodsBean.resid);
            cv.put("num", goodsBean.num);
            cv.put("score", goodsBean.score);
            cv.put("progress", goodsBean.progress);
            cv.put("precent", goodsBean.precent);
            cv.put("hot", goodsBean.hot == true ? 1 : 0);
            cv.put("point", goodsBean.point);
            cv.put("starttime", goodsBean.starttime);
            cv.put("period", goodsBean.period);
            cv.put("endtime", goodsBean.endtime);
            cv.put("claimed", goodsBean.claimed == true ? 1 : 0);
            try {
                MyDBHelper.create(context).replace(dbName, cv, "");
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
    }

    //将竞拍的商品插入
    public static void insertGoods(Context context, GoodsBean goodsBean, String dbName) {
        if (context == null || goodsBean == null) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put("id", goodsBean.id);
        cv.put("code", goodsBean.code);
        cv.put("name", goodsBean.name);
        cv.put("category", goodsBean.category);
        cv.put("price", goodsBean.price);
        cv.put("desc", goodsBean.desc);
        cv.put("resid", goodsBean.resid);
        cv.put("num", goodsBean.num);
        cv.put("score", goodsBean.score);
        cv.put("progress", goodsBean.progress);
        cv.put("precent", goodsBean.precent);
        cv.put("hot", goodsBean.hot == true ? 1 : 0);
        cv.put("point", goodsBean.point);
        cv.put("starttime", goodsBean.starttime);
        cv.put("period", goodsBean.period);
        cv.put("endtime", goodsBean.endtime);
        cv.put("claimed", goodsBean.claimed == true ? 1 : 0);
        try {
            MyDBHelper.create(context).replace(dbName, cv, "");
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改TB_GOODS表中的claimed
     */
    public static void updateClaimed(Context context, String code) {
        if (context == null || TextUtils.isEmpty(code)) {
            return;
        }
        try {
            ContentValues cv = new ContentValues();
            cv.put("claimed", 1);
            MyDBHelper.create(context).update(MyDBHelper.TB_GOODS, cv, "code = ?", new String[]{code});
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    //查询TB_GOODS中竞拍时间未过的所有商品
    public static List<GoodsBean> queryDbNoEndGoods(Context context) {
        List<GoodsBean> goodsBeans = new ArrayList<GoodsBean>();
        long current = System.currentTimeMillis() / 1000;
        Cursor cursor = MyDBHelper.create(context).rawQuery("select * from " + MyDBHelper.TB_GOODS + " where endtime > ?",
                new String[]{"" + current});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                GoodsBean goodsBean = new GoodsBean();
                long endtime = cursor.getLong(cursor.getColumnIndex("endtime"));
                int num = cursor.getInt(cursor.getColumnIndex("num"));
                int count = cursor.getInt(cursor.getColumnIndex("price"));
                long subTime = (endtime - current);
                Logger.i("endtime:" + endtime + ",subTime:" + subTime);
                Logger.i("num:" + num + ",count:" + count);
                long sub = (subTime * count) / (24 * 60 * 60);
                Logger.i("sub:" + sub);
                int subnum = count - ((int) sub);
                Logger.i("subnum:" + subnum);
                if (subnum + 30 > count) {
                    subnum = count - 30;
                }
                if (subnum < 0) {
                    subnum = RandomUtils.random(count);
                }
                int progress = (subnum * 100) / count;

                goodsBean.setId(cursor.getString(cursor.getColumnIndex("id")));
                goodsBean.setCode(cursor.getString(cursor.getColumnIndex("code")));
                goodsBean.setName(cursor.getString(cursor.getColumnIndex("name")));
                goodsBean.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
                goodsBean.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                goodsBean.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                goodsBean.setResid(cursor.getInt(cursor.getColumnIndex("resid")));
                goodsBean.setNum(subnum);
                goodsBean.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                goodsBean.setProgress(progress);
                goodsBean.setPrecent(cursor.getString(cursor.getColumnIndex("precent")));
                goodsBean.setHot((cursor.getInt(cursor.getColumnIndex("hot")) == 1) ? true : false);
                goodsBean.setStarttime(cursor.getLong(cursor.getColumnIndex("starttime")));
                goodsBean.setPeriod(cursor.getLong(cursor.getColumnIndex("period")));
                goodsBean.setEndtime(cursor.getLong(cursor.getColumnIndex("endtime")));
                goodsBean.setPoint(cursor.getInt(cursor.getColumnIndex("point")));
                goodsBean.setClaimed((cursor.getInt(cursor.getColumnIndex("claimed")) == 1) ? true : false);
                goodsBeans.add(goodsBean);
            }
        }
        return goodsBeans;
    }

    //查询TB_CLAIM中所有的商品
    public static List<GoodsBean> queryCliamGoods(Context context) {
        List<GoodsBean> goodsBeans = new ArrayList<GoodsBean>();
        Cursor cursor = MyDBHelper.create(context).rawQuery("select * from " + MyDBHelper.TB_CLAIM, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                GoodsBean goodsBean = new GoodsBean();
                long current = System.currentTimeMillis() / 1000;
                long endtime = cursor.getLong(cursor.getColumnIndex("endtime"));
                int num = cursor.getInt(cursor.getColumnIndex("num"));
                int count = cursor.getInt(cursor.getColumnIndex("price"));
                long subTime = (endtime - current);
                Logger.i("endtime:" + endtime + ",subTime:" + subTime);
                Logger.i("num:" + num + ",count:" + count);
                long sub = (subTime * count) / (24 * 60 * 60);
                Logger.i("sub:" + sub);
                int subnum = count - ((int) sub);
                Logger.i("subnum:" + subnum);
                if (subnum > count) {
                    subnum = count;
                }
                if (subnum < 0) {
                    subnum = RandomUtils.random(count);
                }
                int progress = (subnum * 100) / count;

                goodsBean.setId(cursor.getString(cursor.getColumnIndex("id")));
                goodsBean.setCode(cursor.getString(cursor.getColumnIndex("code")));
                goodsBean.setName(cursor.getString(cursor.getColumnIndex("name")));
                goodsBean.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
                goodsBean.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                goodsBean.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                goodsBean.setResid(cursor.getInt(cursor.getColumnIndex("resid")));
                goodsBean.setNum(subnum);
                goodsBean.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                goodsBean.setProgress(progress);
                goodsBean.setPrecent(cursor.getString(cursor.getColumnIndex("precent")));
                goodsBean.setHot((cursor.getInt(cursor.getColumnIndex("hot")) == 1) ? true : false);
                goodsBean.setStarttime(cursor.getLong(cursor.getColumnIndex("starttime")));
                goodsBean.setPeriod(cursor.getLong(cursor.getColumnIndex("period")));
                goodsBean.setEndtime(cursor.getLong(cursor.getColumnIndex("endtime")));
                goodsBean.setPoint(cursor.getInt(cursor.getColumnIndex("point")));
                goodsBean.setClaimed((cursor.getInt(cursor.getColumnIndex("claimed")) == 1) ? true : false);
                goodsBeans.add(goodsBean);
            }
        }
        return goodsBeans;
    }

    //查询TB_GOODS中所有的商品
    public static List<GoodsBean> queryDbGoods(Context context, String dbName) {
        List<GoodsBean> goodsBeans = new ArrayList<GoodsBean>();
        Cursor cursor = MyDBHelper.create(context).rawQuery("select * from " + dbName, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setId(cursor.getString(cursor.getColumnIndex("id")));
                goodsBean.setCode(cursor.getString(cursor.getColumnIndex("code")));
                goodsBean.setName(cursor.getString(cursor.getColumnIndex("name")));
                goodsBean.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
                goodsBean.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                goodsBean.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                goodsBean.setResid(cursor.getInt(cursor.getColumnIndex("resid")));
                goodsBean.setNum(cursor.getInt(cursor.getColumnIndex("num")));
                goodsBean.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                goodsBean.setPrecent(cursor.getString(cursor.getColumnIndex("precent")));
                goodsBean.setHot((cursor.getInt(cursor.getColumnIndex("hot")) == 1) ? true : false);
                goodsBean.setStarttime(cursor.getLong(cursor.getColumnIndex("starttime")));
                goodsBean.setPeriod(cursor.getLong(cursor.getColumnIndex("period")));
                goodsBean.setEndtime(cursor.getLong(cursor.getColumnIndex("endtime")));
                goodsBean.setPoint(cursor.getInt(cursor.getColumnIndex("point")));
                goodsBean.setClaimed((cursor.getInt(cursor.getColumnIndex("claimed")) == 1) ? true : false);
                goodsBeans.add(goodsBean);
            }
        }
        return goodsBeans;
    }

    //根据商品code删除TB_CLAIM中的商品
    public static void deleteClaimedGoods(Context context, String code) {
        try {
            MyDBHelper.create(context).delete(MyDBHelper.TB_CLAIM, "code = ?", new String[]{code});
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
