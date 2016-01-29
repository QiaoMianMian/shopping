package com.android.shopping.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shopping.App;
import com.android.shopping.Contants;
import com.android.shopping.R;
import com.android.shopping.adapter.HomeAdapter;
import com.android.shopping.adapter.MyPagerAdapter;
import com.android.shopping.bean.DataSet;
import com.android.shopping.bean.GoodsBean;
import com.android.shopping.db.MyDBHelper;
import com.android.shopping.utils.DbUtils;
import com.android.shopping.utils.IntentUtils;
import com.android.shopping.utils.ListUtils;
import com.android.shopping.utils.Logger;
import com.android.shopping.utils.MathUtils;
import com.android.shopping.utils.PreferenceUtils;
import com.android.shopping.utils.RandomUtils;
import com.android.shopping.utils.StatisticsUtils;
import com.android.shopping.widget.DepthPageTransformer;
import com.android.shopping.widget.LineGridView;
import com.android.shopping.widget.PageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 */
public class HomeFragment extends Fragment {
    private final int MSG_HEADER_REFLESH = 0;
    private final int MSG_NOTIFY_REFLESH = 1;
    private final int MSG_CLEAR_MEMORY = 2;//用来清除动画后留下的垃圾
    private int SECOND = 1000;
    private final int HEADER_DURATION = 6 * SECOND;
    private final int NOTIFY_DURATION = 8 * SECOND;

    private int mPosition;
    private int HOME_SPLASH_MAX = 0;
    private ViewPager frg_home_vp;
    private PageIndicator fragment_indicator;
    private TextView frgHeaderCredits;
    private TextView frg_home_notify;
    private LineGridView frg_home_gv;
    private HomeAdapter mHomeAdapter;
    private ImageView frg_home_horn;

    private FrameLayout animation_viewGroup;
    private boolean isClean = false;

    private ArrayList<View> mSplashList = new ArrayList<View>();
    private List<GoodsBean> mGoodsList = new ArrayList<GoodsBean>();

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Contants.BR_GET_POINTS.equalsIgnoreCase(action)) {
                setCredits();
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_HEADER_REFLESH:
                    if (frg_home_vp != null) {
                        if (mPosition < (HOME_SPLASH_MAX - 1)) {
                            frg_home_vp.setCurrentItem(mPosition + 1);
                        } else {
                            frg_home_vp.setCurrentItem(0);
                        }
                    }
                    mHandler.removeMessages(MSG_HEADER_REFLESH);
                    mHandler.sendEmptyMessageDelayed(MSG_HEADER_REFLESH, HEADER_DURATION);
                    break;
                case MSG_NOTIFY_REFLESH:
                    initNotifyText();
                    mHandler.removeMessages(MSG_NOTIFY_REFLESH);
                    mHandler.sendEmptyMessageDelayed(MSG_NOTIFY_REFLESH, NOTIFY_DURATION);
                    break;
                case MSG_CLEAR_MEMORY:
                    try {
                        animation_viewGroup.removeAllViews();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isClean = false;
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeaderDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        initViewPager();
        return view;
    }

    private void initFilter() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Contants.BR_GET_POINTS);
        getActivity().registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initGridView();
        initNotifyText();
        initFilter();
        mHandler.sendEmptyMessageDelayed(MSG_HEADER_REFLESH, HEADER_DURATION);
        mHandler.sendEmptyMessageDelayed(MSG_NOTIFY_REFLESH, NOTIFY_DURATION);

        StatisticsUtils.onResume(getActivity());
        StatisticsUtils.event(getActivity(), StatisticsUtils.SS_FRG_HOME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeMessages(MSG_HEADER_REFLESH);
        mHandler.removeMessages(MSG_NOTIFY_REFLESH);
        StatisticsUtils.onPause(getActivity());
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void setCredits() {
        if (frgHeaderCredits != null) {
            String credits = "" + PreferenceUtils.getPrefInt(getActivity(), PreferenceUtils.PRF_USER_POINTS, 0);
            frgHeaderCredits.setText(credits);
        }
    }

    public void initViews(View view) {
        animation_viewGroup = createAnimLayout();
        frg_home_vp = (ViewPager) view.findViewById(R.id.frg_home_vp);
        fragment_indicator = (PageIndicator) view.findViewById(R.id.frg_home_indicator);
        frgHeaderCredits = (TextView) view.findViewById(R.id.frgHeaderCredits);
        setCredits();
        frg_home_notify = (TextView) view.findViewById(R.id.frg_home_notify);
        frg_home_gv = (LineGridView) view.findViewById(R.id.frg_home_gv);
        frg_home_gv.setFocusable(false);
        frg_home_horn = (ImageView) view.findViewById(R.id.frg_home_horn);
        frg_home_horn.setBackgroundResource(R.drawable.anim_horn);
        AnimationDrawable anim = (AnimationDrawable) frg_home_horn.getBackground();
        anim.start();
    }

    @Override
    public void onLowMemory() {
        isClean = true;
        try {
            animation_viewGroup.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isClean = false;
        super.onLowMemory();
    }

    public void initHeaderDatas() {
        List<Integer> resID = DataSet.getAllHomeFlashsRes();
        if (resID == null) {
            return;
        }
        HOME_SPLASH_MAX = resID.size();
        for (int i = 0; i < HOME_SPLASH_MAX; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(resID.get(i));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(lp);
            mSplashList.add(iv);
        }
    }

    private void initGoodsDatas() {
        mGoodsList = DataSet.getShowGoodsList(getActivity());
        DbUtils.insertDbGoods(getActivity(), mGoodsList, MyDBHelper.TB_GOODS);
    }

    private void initNotifyText() {
        if (frg_home_notify != null) {
            final String txt = "<strong>" + RandomUtils.getLuckerName() + "</strong>"
                    + " won "
                    + "<font color='red'>" + RandomUtils.getLuckerGoods() + "</font> ";
            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.txt_to_top);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Animation anim1 = AnimationUtils.loadAnimation(getActivity(), R.anim.txt_from_bottom);
                    frg_home_notify.setAnimation(anim1);
                    frg_home_notify.setText(Html.fromHtml(txt));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            frg_home_notify.startAnimation(anim);
//            frg_home_notify.setFocusable(true);
//            frg_home_notify.setFocusableInTouchMode(true);
        }
    }

    private void initViewPager() {
        ArrayList<PageIndicator.PageMarkerResources> markers = new ArrayList<PageIndicator.PageMarkerResources>();
        for (int i = 0; i < HOME_SPLASH_MAX; i++) {
            markers.add(new PageIndicator.PageMarkerResources());
        }
        fragment_indicator.addMarkers(markers, true, true);
        fragment_indicator.setActiveMarker(0);

        frg_home_vp.setAdapter(new MyPagerAdapter(getActivity(), mSplashList));
        frg_home_vp.setPageTransformer(true, new DepthPageTransformer());
        frg_home_vp.setCurrentItem(0);
        frg_home_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position % HOME_SPLASH_MAX;
                fragment_indicator.setActiveMarker(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initGridView() {
        initGoodsDatas();
        mHomeAdapter = new HomeAdapter(getActivity(), mGoodsList);
        mHomeAdapter.setOnHolderClickListener(new HomeAdapter.HolderClickListener() {
            @Override
            public void onHolderClick(GoodsBean goodsBean, int[] start_location) {
                App.goods.add(goodsBean);
                ListUtils.removeRepeat(App.goods);
                doAnim(goodsBean.resid, start_location);
                IntentUtils.sendBroadCast(getActivity(), Contants.BR_LIST_TAG);
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_HOME_CLICK_ITEM, "goodsname", "" + goodsBean.name);
            }
        });
        frg_home_gv.setAdapter(mHomeAdapter);
    }

    private void doAnim(int resid, int[] start_location) {
        if (!isClean) {
            setAnim(resid, start_location);
        } else {
            try {
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(resid, start_location);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isClean = true;
            }
        }
    }

    /**
     * 创建动画层
     */
    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将执行动画的view添加到动画层
     */
    private View addViewToAnimLayout(ViewGroup vg, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                MathUtils.dip2px(getActivity(), 90), MathUtils.dip2px(getActivity(), 90));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 动画效果设置
     */
    private void setAnim(int resid, int[] start_location) {
        Animation mScaleAnimation = new ScaleAnimation(1.5f, 0.0f, 1.5f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0.1f);
        mScaleAnimation.setDuration(SECOND);
        mScaleAnimation.setFillAfter(true);
        final ImageView iview = new ImageView(getActivity());
        iview.setImageResource(resid);
        final View view = addViewToAnimLayout(animation_viewGroup, iview, start_location);
        view.setAlpha(0.6f);
        int W = MathUtils.getScreenWidth(getActivity());
        int H = MathUtils.getScreenHeight(getActivity());
        int endX = 5 * W / 8 - start_location[0];
        int endY = (H - MathUtils.dip2px(getActivity(), 20)) - start_location[1];
        Animation mTranslateAnimation = new TranslateAnimation(0, endX, 0, endY);
        Animation mRotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(SECOND);
        mTranslateAnimation.setDuration(SECOND);
        AnimationSet mAnimationSet = new AnimationSet(true);
        mAnimationSet.setFillAfter(true);
        mAnimationSet.addAnimation(mRotateAnimation);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mTranslateAnimation);
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isClean = true;
                mHandler.sendEmptyMessage(MSG_CLEAR_MEMORY);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(mAnimationSet);
    }
}
