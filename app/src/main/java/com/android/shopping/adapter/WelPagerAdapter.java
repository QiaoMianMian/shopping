package com.android.shopping.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.android.shopping.utils.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/29.
 */
public class WelPagerAdapter extends PagerAdapter {
    private ArrayList<View> mList;

    public WelPagerAdapter(ArrayList<View> mList) {
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
        container.addView(mList.get(position));
        return mList.get(position);
    }
}
