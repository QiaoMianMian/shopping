package com.android.shopping;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.shopping.adapter.WelPagerAdapter;
import com.android.shopping.utils.PreferenceUtils;
import com.android.shopping.utils.StatisticsUtils;
import com.android.shopping.widget.DepthPageTransformer;
import com.android.shopping.widget.PageIndicator;

import java.util.ArrayList;


/**
 * Created by Administrator on 2015/12/29.
 */
public class WelcomeActivity extends BaseActivity {
    private final int PAGER_NUMBER = 3;
    private ViewPager mWelVp;
    private PageIndicator mWelInd;
    private ArrayList<View> mWelList = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        hideHeader();
        initDatas();
        initViews();
        initWelPager();
    }

    private void initDatas() {
        final int[] resID = {R.mipmap.ic_wel_1, R.mipmap.ic_wel_2, R.mipmap.ic_wel_3};
        for (int i = 0; i < PAGER_NUMBER; i++) {
            if (i == PAGER_NUMBER - 1) {
                View view = getLayoutInflater().inflate(R.layout.activity_wel_item, null);
                ImageView iv3 = (ImageView) view.findViewById(R.id.wel_img_3);
                iv3.setImageResource(resID[i]);
                ImageView btn = (ImageView) view.findViewById(R.id.wel_btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StatisticsUtils.event(WelcomeActivity.this, StatisticsUtils.SS_TRY_NOW);
                        goAction(MainActivity.class);
                        PreferenceUtils.setPrefBoolean(WelcomeActivity.this, PreferenceUtils.PRF_FIRST_LOGIN, false);
                        finish();
                    }
                });
                mWelList.add(view);
                break;
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                ImageView iv = new ImageView(this);
                iv.setImageResource(resID[i]);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setLayoutParams(lp);
                mWelList.add(iv);
            }
        }
    }

    private void initViews() {
        mWelVp = (ViewPager) this.findViewById(R.id.activity_wel_vp);
        mWelInd = (PageIndicator) this.findViewById(R.id.activity_wel_indicator);
    }

    private void initWelPager() {
        ArrayList<PageIndicator.PageMarkerResources> markers = new ArrayList<PageIndicator.PageMarkerResources>();
        for (int i = 0; i < PAGER_NUMBER; i++) {
            markers.add(new PageIndicator.PageMarkerResources());
        }
        mWelInd.addMarkers(markers, true, true);
        mWelInd.setActiveMarker(0);

        mWelVp.setAdapter(new WelPagerAdapter(mWelList));
        mWelVp.setPageTransformer(true, new DepthPageTransformer());
        mWelVp.setCurrentItem(0);
        mWelVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                mWelInd.setActiveMarker(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

}
