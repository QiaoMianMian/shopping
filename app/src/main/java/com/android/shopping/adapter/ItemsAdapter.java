package com.android.shopping.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.android.shopping.R;
import com.android.shopping.utils.HolderUtils;

public class ItemsAdapter extends BaseAdapter {
	private int mPosition;
	private String[] mItems;
	private Context mContext;
	private ItemStyle mStyle = ItemStyle.SINGLE_CHOICE;

	public ItemsAdapter(Context context, String[] items, int defaultPosition,
						DialogInterface.OnClickListener listener) {
		this.mContext = context;
		this.mPosition = defaultPosition;
		this.mItems = items;
	}

	public ItemsAdapter(Context context, String[] items, ItemStyle style,
						DialogInterface.OnClickListener listener) {
		this.mContext = context;
		this.mItems = items;
		this.mStyle = style;
	}

	public ItemsAdapter(Context context, String[] items,
						DialogInterface.OnClickListener listener) {
		this.mContext = context;
		this.mItems = items;
	}

	@Override
	public int getCount() {
		return mItems.length;
	}

	@Override
	public Object getItem(int position) {
		return mItems[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.dialog_item_layout, parent, false);
		}
		switch (mStyle) {
		case ITEM:
			HolderUtils.get(convertView, R.id.checked_text_view).setVisibility(View.GONE);
			TextView text = HolderUtils.get(convertView, R.id.txtText);
			text.setVisibility(View.VISIBLE);
			text.setText(mItems[position]);
			break;
		case SINGLE_CHOICE:
			final CheckedTextView checkedTextView = HolderUtils.get(convertView, R.id.checked_text_view);
			checkedTextView.setText(mItems[position]);
			if (position == mPosition) {
				checkedTextView.setChecked(true);
			} else {
				checkedTextView.setChecked(false);
			}
			break;
		case MULTI_CHOICE:

			break;
		default:
			break;
		}
		return convertView;
	}

	public void setPosition(int position) {
		mPosition = position;
	}

	public enum ItemStyle {
		ITEM, SINGLE_CHOICE, MULTI_CHOICE
	}
}