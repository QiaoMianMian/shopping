package com.android.shopping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.shopping.adapter.MainPagerAdapter;
import com.android.shopping.bean.GoodsBean;
import com.android.shopping.fragment.HomeFragment;
import com.android.shopping.fragment.ListFragment;
import com.android.shopping.fragment.MeFragment;
import com.android.shopping.fragment.TaskFragment;
import com.android.shopping.utils.DbUtils;
import com.android.shopping.utils.DialogUtils;
import com.android.shopping.utils.ListUtils;
import com.android.shopping.utils.PreferenceUtils;
import com.android.shopping.utils.TapjoyEx;
import com.android.shopping.utils.ToastUtils;
import com.viewpagerindicator.TabPageIndicator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Administrator on 2015/12/28.
 */
public class MainActivity extends BaseActivity {
    private Fragment[] mFragments = {new HomeFragment(), new TaskFragment(), new ListFragment(), new MeFragment()};
    private ViewPager mViewPager;
    private TabPageIndicator indicator;
    private View mLoadingView;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Contants.BR_GO_HOME.equalsIgnoreCase(action)) {
                mViewPager.setCurrentItem(0);
            } else if (Contants.BR_LIST_TAG.equalsIgnoreCase(action)) {
                if (indicator != null) {
                    indicator.setBadgeView(App.goods.size());
                }
            } else if (Contants.BR_LOADING_SHOW.equalsIgnoreCase(action)) {
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.VISIBLE);
                }
            } else if (Contants.BR_LOADING_HIDE.equalsIgnoreCase(action)) {
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
            } else if (Contants.BR_SPEND_SUCCESS.equalsIgnoreCase(action)) {
                List<GoodsBean> mClaimedGoods = DbUtils.queryCliamGoods(context);
                App.goods.addAll(mClaimedGoods);
                ListUtils.removeRepeat(App.goods);
                if (indicator != null) {
                    indicator.setBadgeView(App.goods.size());
                }
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                ToastUtils.showToast(context, "Successed to spend currency");
            } else if (Contants.BR_SPEND_FAILED.equalsIgnoreCase(action)) {
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                ToastUtils.showToast(context, "Failed to spend currency");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean first = PreferenceUtils.getPrefBoolean(this, PreferenceUtils.PRF_FIRST_LOGIN, true);
        if (first) {
            goAction(WelcomeActivity.class);
            finish();
        }
        TapjoyEx.connectTapjoy(this);
        initViews();
        hideHeader();
        initFilter();
//        getFbKey();
    }

    private void getFbKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.android.shopping",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash", "" + e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash", "" + e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void initFilter() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Contants.BR_GO_HOME);
        mFilter.addAction(Contants.BR_LIST_TAG);
        mFilter.addAction(Contants.BR_LOADING_SHOW);
        mFilter.addAction(Contants.BR_LOADING_HIDE);
        mFilter.addAction(Contants.BR_SPEND_SUCCESS);
        mFilter.addAction(Contants.BR_SPEND_FAILED);
        this.registerReceiver(mReceiver, mFilter);
    }

    public void initViews() {
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager = (ViewPager) this.findViewById(R.id.pager);
        mViewPager.setAdapter(pagerAdapter);

        indicator = (TabPageIndicator) this.findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        indicator.setBadgeView(App.goods.size());
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        mLoadingView = DialogUtils.createLoading(this);
        mLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
            }
        });
        ViewGroup mViewGroup = (ViewGroup) this.getWindow().getDecorView();
        mViewGroup.addView(mLoadingView);
    }

}
