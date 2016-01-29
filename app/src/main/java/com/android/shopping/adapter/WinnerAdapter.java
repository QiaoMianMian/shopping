package com.android.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shopping.R;
import com.android.shopping.bean.WinnerBean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 */
public class WinnerAdapter extends BaseAdapter {
    private List<WinnerBean> mWinnerBeans;
    private Context mContext;
    private LayoutInflater mInflater;

    public WinnerAdapter(Context context, List<WinnerBean> winnerBeans) {
        this.mContext = context;
        this.mWinnerBeans = winnerBeans;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mWinnerBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mWinnerBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder mHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.activity_detail_item, null);
            mHolder = new ViewHolder();
            mHolder.icon = (ImageView) view.findViewById(R.id.mWinnerIcon);
            mHolder.name = (TextView) view.findViewById(R.id.mWinnerName);
            mHolder.city = (TextView) view.findViewById(R.id.mWinnerCity);
            mHolder.ip = (TextView) view.findViewById(R.id.mWinnerIp);
            mHolder.time = (TextView) view.findViewById(R.id.mWinnerTime);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }
        WinnerBean winnerBean = mWinnerBeans.get(position);
        if (winnerBean != null) {
            mHolder.icon.setImageResource(winnerBean.iconId);
            mHolder.name.setText(winnerBean.name);
            mHolder.city.setText(winnerBean.city);
            mHolder.ip.setText(winnerBean.ip);
            mHolder.time.setText(winnerBean.time);
        }
        return view;
    }

    class ViewHolder {
        ImageView icon;
        TextView city;
        TextView name;
        TextView ip;
        TextView time;
    }
}
