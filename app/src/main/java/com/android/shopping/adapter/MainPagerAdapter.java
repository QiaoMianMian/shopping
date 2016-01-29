package com.android.shopping.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.shopping.R;
import com.viewpagerindicator.IconPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    private Fragment[] mFragments;
    private static final String[] CONTENT = new String[]{"Rewards", "Task", "List", "Me"};
    private static final int[] ICONS = new int[]{
            R.drawable.ic_home_selector,
            R.drawable.ic_task_selector,
            R.drawable.ic_list_selector,
            R.drawable.ic_person_selector,
    };

    public MainPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
        return ICONS[index];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }
}
