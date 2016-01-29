package com.android.shopping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.shopping.adapter.WinnerAdapter;
import com.android.shopping.bean.DataSet;
import com.android.shopping.bean.GoodsBean;
import com.android.shopping.bean.WinnerBean;
import com.android.shopping.utils.DialogUtils;
import com.android.shopping.utils.Logger;
import com.android.shopping.utils.StatisticsUtils;
import com.android.shopping.utils.ToastUtils;
import com.android.shopping.widget.ListViewEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 */
public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mDetailBack;
    private ImageView mDetailIcon;
    private TextView mDetailState;
    private TextView mDetailName;
    private TextView mDetailNumber;
    private ProgressBar mDetailProgress;
    private TextView mDetailCount;
    private TextView mDetailNeed;
    private TextView mDetailDraw;
    private ListViewEx mDetailWinner;
    private GoodsBean goodsBean;
    private View mLoadingView;

    private List<WinnerBean> mWinnerBeans = new ArrayList<WinnerBean>();

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logger.i("action:" + action);
            if (Contants.BR_LOADING_SHOW.equalsIgnoreCase(action)) {
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.VISIBLE);
                }
            } else if (Contants.BR_LOADING_HIDE.equalsIgnoreCase(action)) {
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
            } else if (Contants.BR_SPEND_SUCCESS.equalsIgnoreCase(action)) {
                setClaimState();
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
            } else if (Contants.BR_SPEND_FAILED.equalsIgnoreCase(action)) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        hideHeader();
        initDatas();
        initViews();
        initFilter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatisticsUtils.onResume(this);
        StatisticsUtils.event(this, StatisticsUtils.SS_DETAIL_GOODS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatisticsUtils.onPause(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void initFilter() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Contants.BR_LOADING_SHOW);
        mFilter.addAction(Contants.BR_LOADING_HIDE);
        mFilter.addAction(Contants.BR_SPEND_SUCCESS);
        mFilter.addAction(Contants.BR_SPEND_FAILED);
        this.registerReceiver(mReceiver, mFilter);
    }

    private void initDatas() {
        goodsBean = (GoodsBean) this.getIntent().getSerializableExtra("goodsBean");
        mWinnerBeans = DataSet.getWinnerList();
    }

    private void initViews() {
        mDetailBack = (ImageView) this.findViewById(R.id.mDetailBack);
        mDetailBack.setOnClickListener(this);
        mDetailIcon = (ImageView) this.findViewById(R.id.mDetailIcon);
        mDetailIcon.setImageResource(goodsBean.resid);
        mDetailState = (TextView) this.findViewById(R.id.mDetailState);
        mDetailName = (TextView) this.findViewById(R.id.mDetailName);
        mDetailName.setText(goodsBean.name);
        mDetailNumber = (TextView) this.findViewById(R.id.mDetailNumber);
        mDetailNumber.setText("Item No." + goodsBean.code);
        mDetailProgress = (ProgressBar) this.findViewById(R.id.mDetailProgress);
        mDetailProgress.setProgress(goodsBean.progress);
        mDetailCount = (TextView) this.findViewById(R.id.mDetailCount);
        mDetailCount.setText(goodsBean.price + "Participants Required");
        mDetailNeed = (TextView) this.findViewById(R.id.mDetailNeed);
        mDetailNeed.setText((goodsBean.price - goodsBean.num) + "Left");
        mDetailDraw = (TextView) this.findViewById(R.id.mDetailDraw);
        mDetailDraw.setOnClickListener(this);
        mDetailWinner = (ListViewEx) this.findViewById(R.id.mDetailWinner);
        WinnerAdapter mAdpater = new WinnerAdapter(this, mWinnerBeans);
        mDetailWinner.setAdapter(mAdpater);
        mDetailWinner.setFocusable(false);

        if (goodsBean.isClaimed()) {
            setClaimState();
        }

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

    private void setClaimState() {
        if (mDetailDraw != null) {
            mDetailDraw.setText("Claimed");
            mDetailDraw.setBackgroundColor(Color.parseColor("#ADADAD"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mDetailBack:
                finish();
                break;
            case R.id.mDetailDraw:
                DialogUtils.dialogShow(this, goodsBean);
                StatisticsUtils.event(this, StatisticsUtils.SS_DETAIL_CLAIM, "goosname", goodsBean.name);
                break;
        }
    }
}
