package com.android.shopping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.shopping.R;
import com.android.shopping.adapter.TaskAdapter;
import com.android.shopping.bean.AppBean;
import com.android.shopping.utils.AppUtils;
import com.android.shopping.utils.Logger;
import com.android.shopping.utils.ShareUtils;
import com.android.shopping.utils.StatisticsUtils;
import com.android.shopping.utils.TapjoyEx;
import com.android.shopping.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 */
public class TaskFragment extends Fragment implements View.OnClickListener {
    private ListView mTaskOfferwall;
    private List<AppBean> mList = new ArrayList<AppBean>();

    private ImageView mTaskShareFb;
    private ImageView mTaskShareWhatsapp;
    private ImageView mTaskShareMore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, null);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        StatisticsUtils.onResume(getActivity());
        StatisticsUtils.event(getActivity(), StatisticsUtils.SS_FRG_TASK);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatisticsUtils.onPause(getActivity());
    }

    private void initDatas() {
        mList.add(new AppBean(R.mipmap.ic_task_1, "tapjoy", "31,58701 people online",
                getString(R.string.task_desc)));
        mList.add(new AppBean(R.mipmap.ic_task_2, "trialpay", "64,1047 people online",
                getString(R.string.task_desc)));
    }

    private void initViews(View view) {
        mTaskShareFb = (ImageView) view.findViewById(R.id.mTaskShareFb);
        mTaskShareFb.setOnClickListener(this);
        mTaskShareWhatsapp = (ImageView) view.findViewById(R.id.mTaskShareWhatsapp);
        mTaskShareWhatsapp.setOnClickListener(this);
        mTaskShareMore = (ImageView) view.findViewById(R.id.mTaskShareMore);
        mTaskShareMore.setOnClickListener(this);
        mTaskOfferwall = (ListView) view.findViewById(R.id.mTaskOfferwall);
        mTaskOfferwall.setAdapter(new TaskAdapter(getActivity(), mList));
        mTaskOfferwall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_TASK_ITEM, "position", "" + position);
                TapjoyEx.offerWallTapjoy(getActivity());
            }
        });
        setIconState();
    }

    private void setIconState() {
        if (!AppUtils.isInstallApp(getActivity(), ShareUtils.ShareFBAPP)) {
            mTaskShareFb.setImageResource(R.mipmap.share_facebook_pressed);
        }
        if (!AppUtils.isInstallApp(getActivity(), ShareUtils.ShareWHAPP)) {
            mTaskShareWhatsapp.setImageResource(R.mipmap.share_whatsapp_pressed);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTaskShareFb:
                if (AppUtils.isInstallApp(getActivity(), ShareUtils.ShareFBAPP)) {
                    ShareUtils.shareTo(getActivity(), ShareUtils.ShareFBAPP, ShareUtils.ShareMsg);
                } else {
                    ToastUtils.showToast(getActivity(), "This application is not installed");
                }
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_TASK_SHAREFB);
                break;
            case R.id.mTaskShareWhatsapp:
                if (AppUtils.isInstallApp(getActivity(), ShareUtils.ShareWHAPP)) {
                    ShareUtils.shareTo(getActivity(), ShareUtils.ShareWHAPP, ShareUtils.ShareMsg);
                } else {
                    ToastUtils.showToast(getActivity(), "This application is not installed");
                }
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_TASK_SHAREWP);
                break;
            case R.id.mTaskShareMore:
                ShareUtils.shareTo(getActivity(), "", ShareUtils.ShareMsg);
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_TASK_SHAREMR);
                break;
        }
    }
}
