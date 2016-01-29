package com.android.shopping.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shopping.App;
import com.android.shopping.Contants;
import com.android.shopping.R;
import com.android.shopping.SubThread;
import com.android.shopping.adapter.ListAdapter;
import com.android.shopping.bean.GoodsBean;
import com.android.shopping.db.MyDBHelper;
import com.android.shopping.utils.DbUtils;
import com.android.shopping.utils.DialogUtils;
import com.android.shopping.utils.IntentUtils;
import com.android.shopping.utils.ListUtils;
import com.android.shopping.utils.Logger;
import com.android.shopping.utils.StatisticsUtils;
import com.android.shopping.utils.TapjoyEx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ListFragment extends Fragment implements View.OnClickListener {
    private TextView mEditView;
    private ListView mListView;
    private RelativeLayout frgListGoodsEditRlt;
    private TextView frgListGoodsNum; //总商品数量
    private TextView frgListGoodsPoints; //总商品点数(价格)
    private TextView frgListGoodsAccount; //提交按钮(结账)
    private RelativeLayout frgListGoodsDeleteRlt;
    private CheckBox frgListItemAllSelected; //全选按钮
    private TextView frgListGoodsDelNum; //删除时总商品数量
    private TextView frgListGoodsDeleteBtn; //删除按钮
    private RelativeLayout frgListGoodsEmptyRlt;
    private TextView frgListGoodsEmptyGo; //跳到Home

    private ListAdapter mAdapter;
    private List<GoodsBean> mGoodsList = new ArrayList<GoodsBean>();
    private boolean isEdit;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Contants.BR_GET_POINTS.equalsIgnoreCase(action)) {
                if (mAdapter != null) {
                    mAdapter.setTxt();
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFilter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewDatas();
        StatisticsUtils.onResume(getActivity());
        StatisticsUtils.event(getActivity(), StatisticsUtils.SS_FRG_LIST);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatisticsUtils.onPause(getActivity());
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void initFilter() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Contants.BR_GET_POINTS);
        getActivity().registerReceiver(mReceiver, mFilter);
    }

    private void initViewDatas() {
//        List<GoodsBean> mClaimedGoods = DbUtils.queryCliamGoods(getActivity());
//        App.goods.addAll(mClaimedGoods);
//        ListUtils.removeRepeatPre(App.goods);
        mGoodsList = App.goods;

        mAdapter = new ListAdapter(getActivity(), mGoodsList);
        mAdapter.setViewId(frgListGoodsNum, frgListGoodsPoints, frgListGoodsDelNum);
        mListView.setAdapter(mAdapter);
        setViewVisible();
    }

    private void initViews(View view) {
        mEditView = (TextView) view.findViewById(R.id.frg_top_edit);
        mEditView.setOnClickListener(this);

        frgListGoodsEditRlt = (RelativeLayout) view.findViewById(R.id.frgListGoodsEditRlt);
        frgListGoodsNum = (TextView) view.findViewById(R.id.frgListGoodsNum);
        frgListGoodsPoints = (TextView) view.findViewById(R.id.frgListGoodsPoints);
        frgListGoodsAccount = (TextView) view.findViewById(R.id.frgListGoodsAccount);
        frgListGoodsAccount.setOnClickListener(this);
        frgListGoodsDeleteRlt = (RelativeLayout) view.findViewById(R.id.frgListGoodsDeleteRlt);
        frgListItemAllSelected = (CheckBox) view.findViewById(R.id.frgListItemAllSelected);
        frgListItemAllSelected.setOnClickListener(this);
        frgListGoodsDelNum = (TextView) view.findViewById(R.id.frgListGoodsDelNum);
        frgListGoodsDeleteBtn = (TextView) view.findViewById(R.id.frgListGoodsDeleteBtn);
        frgListGoodsDeleteBtn.setOnClickListener(this);
        frgListGoodsEmptyRlt = (RelativeLayout) view.findViewById(R.id.list_empty_rlt);
        frgListGoodsEmptyGo = (TextView) view.findViewById(R.id.list_empty_go);
        frgListGoodsEmptyGo.setOnClickListener(this);

        mListView = (ListView) view.findViewById(R.id.frg_list_goods);
    }

    private void setViewVisible() {
        if (mGoodsList.size() > 0) {
            mListView.setVisibility(View.VISIBLE);
            frgListGoodsEmptyRlt.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.GONE);
            frgListGoodsEmptyRlt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_top_edit:
                setMode();
                setInitState();
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_LIST_CLICK_EDIT);
                break;
            case R.id.frgListItemAllSelected:
                if (frgListItemAllSelected.isChecked()) {
                    setAllSelected(true);
                } else {
                    setAllSelected(false);
                }
                frgListGoodsDelNum.setText(getString(R.string.total) + " " + mAdapter.getDelNumber()
                        + " " + getString(R.string.items));
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_LIST_CLICK_ALLDEL);
                break;
            case R.id.frgListGoodsAccount:
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_LIST_CLICK_FREE);
                TapjoyEx.offerWallTapjoy(getActivity());
                break;
            case R.id.frgListGoodsDeleteBtn:
                mAdapter.delete();
                isEdit = true;
                setMode();
                mAdapter.setTxt();
                setViewVisible();
                IntentUtils.sendBroadCast(getActivity(), Contants.BR_LIST_TAG);
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_LIST_CLICK_DELETE);
                break;
            case R.id.list_empty_go:
                IntentUtils.sendBroadCast(getActivity(), Contants.BR_GO_HOME);
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_LIST_CLICK_EMPTY);
                break;
        }
    }

    private void setInitState() {
        //删除视图
        frgListItemAllSelected.setChecked(false);
        frgListGoodsDelNum.setText(getString(R.string.total) + " 0 " + getString(R.string.items));
    }

    //是否全选
    private void setAllSelected(boolean flag) {
        List<GoodsBean> allLists = mAdapter.getGoodsList();
        for (GoodsBean goodsBeans : allLists) {
            goodsBeans.setSelected(flag);
        }
        mAdapter.notifyDataSetChanged(true);
    }

    private void setMode() {
        if (isEdit) {
            isEdit = false;
            mEditView.setText(getResources().getString(R.string.txt_top_edit));
        } else {
            isEdit = true;
            mEditView.setText(getResources().getString(R.string.txt_top_complete));
        }
        setVisibility(isEdit);
        mAdapter.notifyDataSetChanged(isEdit);
    }

    private void setVisibility(boolean flag) {
        if (flag) {
            frgListGoodsEditRlt.setVisibility(View.GONE);
            frgListGoodsDeleteRlt.setVisibility(View.VISIBLE);
        } else {
            frgListGoodsEditRlt.setVisibility(View.VISIBLE);
            frgListGoodsDeleteRlt.setVisibility(View.GONE);
        }
    }
}
