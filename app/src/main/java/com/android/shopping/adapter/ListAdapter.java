package com.android.shopping.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shopping.R;
import com.android.shopping.bean.GoodsBean;
import com.android.shopping.utils.AppUtils;
import com.android.shopping.utils.DbUtils;
import com.android.shopping.utils.DialogUtils;
import com.android.shopping.utils.Logger;
import com.android.shopping.utils.MathUtils;
import com.android.shopping.utils.PreferenceUtils;
import com.android.shopping.utils.RandomUtils;
import com.android.shopping.utils.StatisticsUtils;

import java.util.Iterator;
import java.util.List;
import java.util.prefs.PreferencesFactory;

/**
 * Created by Administrator on 2015/12/31.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoodsBean> mGoodsList;
    private LayoutInflater mInflater;
    private TextView frgListGoodsNum; //总商品数量
    private TextView frgListGoodsPoints; //总商品点数(价格)
    private TextView frgListGoodsDelNum; //删除时总商品数量

    private final int basePoins = 1;
    private boolean isEdit = false;
    Handler mHandler = new Handler();

    public int getPoints() {
        int points = PreferenceUtils.getPrefInt(mContext, PreferenceUtils.PRF_USER_POINTS, 0);
//        for (GoodsBean goodsBean : mGoodsList) {
//            points += goodsBean.point;
//        }
        return points;
    }

    public String getAllNumber() {
        return "" + getCount();
    }

    public String getDelNumber() {
        int delNum = 0;
        for (GoodsBean goodsBean : mGoodsList) {
            if (goodsBean.isSelected()) {
                delNum++;
            }
        }
        return "" + delNum;
    }

    public List<GoodsBean> getGoodsList() {
        return mGoodsList;
    }

    public void setGoodsList(List<GoodsBean> mGoodsList) {
        this.mGoodsList = mGoodsList;
    }

    public ListAdapter(Context context, List<GoodsBean> list) {
        this.mContext = context;
        this.mGoodsList = list;
        mInflater = LayoutInflater.from(context);
    }

    public void setViewId(TextView frgListGoodsNum, TextView frgListGoodsPoints, TextView frgListGoodsDelNum) {
        this.frgListGoodsNum = frgListGoodsNum;
        this.frgListGoodsPoints = frgListGoodsPoints;
        this.frgListGoodsDelNum = frgListGoodsDelNum;
        setTxt();
    }

    public void setTxt() {
        String txt1 = "A total of <font color ='red'> " + getAllNumber() + "</font> items";
        frgListGoodsNum.setText(Html.fromHtml(txt1));
        String txt2 = "A total of <font color ='red'>" + getPoints() + "</font> credits";
        frgListGoodsPoints.setText(Html.fromHtml(txt2));
    }

    public void notifyDataSetChanged(boolean isEdit) {
        this.isEdit = isEdit;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mGoodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder mHolder;
//        if (view == null || (view.getTag(R.layout.fragment_list_item + position) == null)) {
        view = mInflater.inflate(R.layout.fragment_list_item, null);
        mHolder = new ViewHolder();
        mHolder.frgListItemSelected = (CheckBox) view.findViewById(R.id.frgListItemSelected);
        mHolder.frgListItemIcon = (ImageView) view.findViewById(R.id.frgListItemIcon);
        mHolder.frgListItemName = (TextView) view.findViewById(R.id.frgListItemName);
        mHolder.frgListItemProgress = (TextView) view.findViewById(R.id.frgListItemProgress);
        mHolder.frgListItemSub = (ImageView) view.findViewById(R.id.frgListItemSub);
        mHolder.frgListItemNum = (EditText) view.findViewById(R.id.frgListItemNum);
        mHolder.frgListItemPlus = (ImageView) view.findViewById(R.id.frgListItemPlus);
        mHolder.frgListItemPoints = (TextView) view.findViewById(R.id.frgListItemPoints);
        mHolder.frgListItemTime = (TextView) view.findViewById(R.id.frgListItemTime);
        mHolder.frgListItemClaim = (TextView) view.findViewById(R.id.frgListItemClaim);
        mHolder.frgListItemPart = (TextView) view.findViewById(R.id.frgListItemPart);
        mHolder.frgListItemCount = (TextView) view.findViewById(R.id.frgListItemCount);
        mHolder.frgListItemCode = (TextView) view.findViewById(R.id.frgListItemCode);
        view.setTag(R.layout.fragment_list_item + position, mHolder);
//        }
//        else {
//            mHolder = (ViewHolder) view.getTag(R.layout.fragment_list_item + position);
//        }
        if (isEdit) {
            mHolder.frgListItemSelected.setVisibility(View.VISIBLE);
        } else {
            mHolder.frgListItemSelected.setVisibility(View.GONE);
        }
        final GoodsBean goodsBean = mGoodsList.get(position);
        if (goodsBean != null) {
            mHolder.frgListItemSelected.setChecked(goodsBean.selected);
            mHolder.frgListItemIcon.setImageResource(goodsBean.resid);
            mHolder.frgListItemIcon.setOnClickListener(new OnClick(mHolder, goodsBean));
            mHolder.frgListItemName.setText(goodsBean.name);
            mHolder.frgListItemProgress.setText(goodsBean.precent);
            mHolder.frgListItemNum.setText("" + goodsBean.point);
            mHolder.frgListItemNum.addTextChangedListener(new TextWatcher(mHolder, goodsBean));
            mHolder.frgListItemSelected.setOnClickListener(new OnClick(mHolder, goodsBean));
            mHolder.frgListItemSub.setOnClickListener(new OnClick(mHolder, goodsBean));
            mHolder.frgListItemPlus.setOnClickListener(new OnClick(mHolder, goodsBean));
            mHolder.frgListItemPoints.setText("" + goodsBean.point);
            mHolder.frgListItemClaim.setOnClickListener(new OnClick(mHolder, goodsBean));
            mHolder.frgListItemCount.setText("(" + goodsBean.price + ")");
            mHolder.frgListItemCode.setText("No." + goodsBean.code);
            setStartState(goodsBean, mHolder);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    long current = System.currentTimeMillis() / 1000;
                    long endtime = goodsBean.getEndtime();
                    long duration = endtime - current;
                    if (duration > 0) {
                        mHolder.frgListItemTime.setText(MathUtils.getTimeFormat(duration));
                        mHandler.postDelayed(this, 1000);
                    } else {
                        setEndState(goodsBean, mHolder);
                    }
                }
            }, 1000);
        }
        return view;
    }

    private void setEndState(GoodsBean goodsBean, ViewHolder mHolder) {
        goodsBean.setPeriod(0);
        mHolder.frgListItemPart.setText("" + goodsBean.price);
        mHolder.frgListItemTime.setText("00:00:00");
        mHolder.frgListItemTime.setTextColor(Color.parseColor("#C4C4C4"));
        mHolder.frgListItemClaim.setText("End");
        mHolder.frgListItemClaim.setBackgroundColor(Color.parseColor("#C4C4C4"));
    }

    private void setStartState(GoodsBean goodsBean, ViewHolder mHolder) {
        long current = System.currentTimeMillis() / 1000;
        long endtime = goodsBean.getEndtime();
        long duration = endtime - current;
        int count = goodsBean.price;
        long sub = (duration * count) / (24 * 60 * 60);
        int subnum = count - ((int) sub);
        if (subnum > count) {
            subnum = count;
        }
        if (subnum < 0) {
            subnum = RandomUtils.random(count);
        }
        if (duration > 0) {
            mHolder.frgListItemTime.setText(MathUtils.getTimeFormat(duration));
            mHolder.frgListItemPart.setText("" + subnum);
            if (goodsBean.isClaimed()) {
                mHolder.frgListItemClaim.setText("Claimed");
                mHolder.frgListItemClaim.setBackgroundColor(Color.parseColor("#EEEEEE"));
            }
        } else {
            setEndState(goodsBean, mHolder);
        }
    }

    public void delete() {
        List<GoodsBean> lists = getGoodsList();
        Iterator<GoodsBean> it = lists.listIterator();
        while (it.hasNext()) {
            GoodsBean goodsBean = it.next();
            if (goodsBean.isSelected()) {
                it.remove();
                DbUtils.deleteClaimedGoods(mContext, goodsBean.code);
            }
        }
        setGoodsList(lists);
    }

    private class TextWatcher implements android.text.TextWatcher {
        private ViewHolder holder;
        private GoodsBean goodsBean;

        public TextWatcher(ViewHolder holder, GoodsBean goodsBean) {
            this.holder = holder;
            this.goodsBean = goodsBean;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s)) {
                int num = Integer.parseInt(s.toString());
                if (num > goodsBean.price) {
                    num = goodsBean.price;
                    holder.frgListItemNum.setText("" + num);
                }
                goodsBean.setPoint(num);
                setTxt();
            }
        }
    }

    private class OnClick implements View.OnClickListener {
        private ViewHolder holder;
        private GoodsBean goodsBean;
        private int points;

        public OnClick(ViewHolder holder, GoodsBean goodsBean) {
            this.holder = holder;
            this.goodsBean = goodsBean;
        }

        @Override
        public void onClick(View v) {
            String txt = holder.frgListItemNum.getText().toString();
            if (TextUtils.isEmpty(txt)) {
                txt = "0";
            }
            points = Integer.parseInt(txt);
            switch (v.getId()) {
                case R.id.frgListItemIcon:
                    StatisticsUtils.event(mContext, StatisticsUtils.SS_LIST_CLICK_ICON, "goosname", goodsBean.name);
                    AppUtils.gotoBrowser(mContext, goodsBean.descurl);
                    break;
                case R.id.frgListItemSub:
                    int subPoint = goodsBean.point;
                    if (points > basePoins) {
                        subPoint = points - basePoins;
                    }
                    holder.frgListItemNum.setText("" + subPoint);
                    goodsBean.setPoint(subPoint);
                    setTxt();
                    StatisticsUtils.event(mContext, StatisticsUtils.SS_LIST_CLICK_SUB);
                    break;
                case R.id.frgListItemPlus:
                    int plusPoint = points + basePoins;
                    holder.frgListItemNum.setText("" + (plusPoint));
                    goodsBean.setPoint(plusPoint);
                    setTxt();
                    StatisticsUtils.event(mContext, StatisticsUtils.SS_LIST_CLICK_PLUS);
                    break;
                case R.id.frgListItemSelected:
                    if (holder.frgListItemSelected.isChecked()) {
                        goodsBean.setSelected(true);
                    } else {
                        goodsBean.setSelected(false);
                    }
                    frgListGoodsDelNum.setText(mContext.getString(R.string.total) + " " + getDelNumber()
                            + " " + mContext.getString(R.string.items));
                    StatisticsUtils.event(mContext, StatisticsUtils.SS_LIST_CLICK_DEL);
                    break;
                case R.id.frgListItemClaim:
                    DialogUtils.dialogShow(mContext, goodsBean);
                    StatisticsUtils.event(mContext, StatisticsUtils.SS_CLAIM_IT, "goosname", goodsBean.name);
                    break;
            }
        }
    }

    public class ViewHolder {
        public CheckBox frgListItemSelected;
        ImageView frgListItemIcon;
        TextView frgListItemName;
        TextView frgListItemProgress;
        ImageView frgListItemSub;
        EditText frgListItemNum;
        ImageView frgListItemPlus;
        TextView frgListItemPoints;
        TextView frgListItemTime;
        TextView frgListItemClaim;
        TextView frgListItemPart;
        TextView frgListItemCount;
        TextView frgListItemCode;
    }

}
