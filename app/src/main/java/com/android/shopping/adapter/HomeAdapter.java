package com.android.shopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shopping.GoodsDetailActivity;
import com.android.shopping.R;
import com.android.shopping.bean.GoodsBean;
import com.android.shopping.utils.RandomUtils;
import com.android.shopping.utils.StatisticsUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/12/30.
 */
public class HomeAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoodsBean> mList;
    private LayoutInflater mInflater;
    private HolderClickListener mHolderClickListener;

    public HomeAdapter(Context context, List<GoodsBean> mList) {
        this.mContext = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder mHolder;
        if (view == null || (view.getTag() == null)) {
            view = mInflater.inflate(R.layout.fragment_home_item, null);
            mHolder = new ViewHolder();
            mHolder.goods_credits = (TextView) view.findViewById(R.id.goods_credits);
            mHolder.goods_pic = (ImageView) view.findViewById(R.id.goods_pic);
            mHolder.goods_name = (TextView) view.findViewById(R.id.goods_name);
            mHolder.goods_hot = (ImageView) view.findViewById(R.id.goods_hot);
            mHolder.goods_num = (TextView) view.findViewById(R.id.goods_num);
            mHolder.goods_all = (TextView) view.findViewById(R.id.goods_all);
            mHolder.goods_star_1 = (ImageView) view.findViewById(R.id.goods_star_1);
            mHolder.goods_star_2 = (ImageView) view.findViewById(R.id.goods_star_2);
            mHolder.goods_star_3 = (ImageView) view.findViewById(R.id.goods_star_3);
            mHolder.goods_star_4 = (ImageView) view.findViewById(R.id.goods_star_4);
            mHolder.goods_star_5 = (ImageView) view.findViewById(R.id.goods_star_5);
            mHolder.goods_claim = (TextView) view.findViewById(R.id.goods_claim);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }
        final GoodsBean goodsBean = mList.get(position);
        if (goodsBean != null) {
            mHolder.goods_credits.setText(goodsBean.point + " Credits");
            mHolder.goods_name.setText(goodsBean.name);
            mHolder.goods_pic.setImageResource(goodsBean.resid);
            if (goodsBean.hot) {
                mHolder.goods_hot.setVisibility(View.GONE);
            }
            mHolder.goods_all.setText("" + goodsBean.price);
            mHolder.goods_num.setText("" + goodsBean.num);
            RandomUtils.setRatingStars(goodsBean.score, mHolder.goods_star_1, mHolder.goods_star_2,
                    mHolder.goods_star_3, mHolder.goods_star_4, mHolder.goods_star_5);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goodsBean", goodsBean);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        mHolder.goods_claim.setOnClickListener(new OnClick(mHolder, goodsBean));
        return view;
    }

    class OnClick implements View.OnClickListener {
        ViewHolder mHolder;
        GoodsBean goodsBean;

        public OnClick(ViewHolder mHolder, GoodsBean goodsBean) {
            this.mHolder = mHolder;
            this.goodsBean = goodsBean;
        }

        @Override
        public void onClick(View v) {
            StatisticsUtils.event(mContext, StatisticsUtils.SS_CLAIM_IT, "goosname", goodsBean.name);
            if (mHolderClickListener != null) {
                int[] start_location = new int[2];
                mHolder.goods_pic.getLocationInWindow(start_location);//获取点击商品图片的位置
                Drawable drawable = mHolder.goods_pic.getDrawable();//复制一个新的商品图标
                mHolderClickListener.onHolderClick(goodsBean, start_location);
            }
        }
    }


    class ViewHolder {
        TextView goods_credits;
        ImageView goods_pic;
        TextView goods_name;
        ImageView goods_hot;
        TextView goods_num;
        TextView goods_all;
        ImageView goods_star_1;
        ImageView goods_star_2;
        ImageView goods_star_3;
        ImageView goods_star_4;
        ImageView goods_star_5;
        TextView goods_claim;
    }

    public void setOnHolderClickListener(HolderClickListener holderClickListener) {
        this.mHolderClickListener = holderClickListener;
    }

    public interface HolderClickListener {
        public void onHolderClick(GoodsBean goodsBean, int[] start_location);
    }
}
