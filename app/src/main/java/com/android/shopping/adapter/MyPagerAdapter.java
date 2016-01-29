package com.android.shopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.android.shopping.GoodsDetailActivity;
import com.android.shopping.bean.DataSet;
import com.android.shopping.bean.GoodsBean;
import com.android.shopping.utils.Logger;
import com.android.shopping.utils.StatisticsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/12/29.
 */
public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> mList;
    private Context mContext;

    public MyPagerAdapter(Context context, ArrayList<View> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View objects = mList.get(position);
        try {
            container.addView(objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatisticsUtils.event(mContext, StatisticsUtils.SS_HOME_CLICK_HEADER, "position", "" + position);
                List<GoodsBean> mGoodsList = DataSet.getAllGoodsList();
                if (position == 2) {
                    goAction(mContext, mGoodsList.get(29));
                } else if (position == 3) {
                    goAction(mContext, mGoodsList.get(10));
                }
            }
        });
        return objects;
    }

    private void goAction(Context context, GoodsBean goodsBean) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goodsBean", goodsBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
