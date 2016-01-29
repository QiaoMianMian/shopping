package com.android.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shopping.R;
import com.android.shopping.bean.AppBean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/6.
 */
public class TaskAdapter extends BaseAdapter {
    private Context mContext;
    private List<AppBean> mList;
    private LayoutInflater mInflater;

    public TaskAdapter(Context context, List<AppBean> mList) {
        this.mContext = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null || (convertView.getTag() == null)) {
            convertView = mInflater.inflate(R.layout.fragment_task_item, null);
            mHolder = new ViewHolder();
            mHolder.iv = (ImageView) convertView.findViewById(R.id.task_item_iv);
            mHolder.name = (TextView) convertView.findViewById(R.id.task_item_name);
            mHolder.online = (TextView) convertView.findViewById(R.id.task_item_online);
            mHolder.desc = (TextView) convertView.findViewById(R.id.task_item_desc);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        AppBean appBean = mList.get(position);
        if (appBean != null) {
            mHolder.iv.setImageResource(appBean.resid);
            mHolder.name.setText(appBean.name);
            mHolder.online.setText(appBean.online);
            mHolder.desc.setText(appBean.desc);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView name;
        TextView online;
        TextView desc;
    }
}
