package com.android.shopping.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.shopping.R;
import com.android.shopping.adapter.ItemsAdapter;

public class CustomDialog extends Dialog {
    public CustomDialog(Context paramContext) {
        super(paramContext);
    }

    public CustomDialog(Context paramContext, int paramInt) {
        super(paramContext, paramInt);
    }

    public static class Builder {
        private CustomDialog dialog;
        private View contentView;
        private Context mContext;
        private OnClickListener mExpendButtonClickListener;
        private String mExpendButtonText;
        private int mIconResId = 0;
        private ItemsAdapter mItemsAdapter;
        private String mMessage;
        private OnClickListener mNegativeButtonClickListener;
        private String mNegativeButtonText;
        private OnClickListener mPositiveButtonClickListener;
        private String mPositiveButtonText;
        private String mTitle;

        public Builder(Context paramContext) {
            this.mContext = paramContext;
            this.dialog = new CustomDialog(this.mContext, R.style.Dialog);
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService("layout_inflater");
            dialog.setCanceledOnTouchOutside(false);
            View view = inflater.inflate(R.layout.dialog_layout, null);
            dialog.addContentView(view, new ViewGroup.LayoutParams(-1, -2));
            TextView titleTxt = (TextView) view.findViewById(R.id.title);
            titleTxt.setText(this.mTitle);
            Button localButton1 = (Button) view.findViewById(R.id.expendButton);
            View expendDevider = view.findViewById(R.id.expendBtnDevider);
            expendDevider.setVisibility(View.GONE);
            int i;
            if (Build.VERSION.SDK_INT < 14) {
                i = 0;
            } else {
                i = 1;
            }
            Object negativeButton;
            Object positiveBtn;
            Button negativeBtn;
            View negativeDevider;
            if (i == 0) {
                negativeButton = view.findViewById(R.id.negativeBtnDevider);
                ((View) negativeButton).setVisibility(View.GONE);
                positiveBtn = (Button) view.findViewById(R.id.positiveButton);
                negativeBtn = (Button) view.findViewById(R.id.negativeButton);
                negativeDevider = null;
            } else {
                negativeDevider = view.findViewById(R.id.negativeBtnDevider);
                negativeDevider.setVisibility(View.GONE);
                negativeButton = (Button) view
                        .findViewById(R.id.negativeButton);
                negativeBtn = (Button) view.findViewById(R.id.positiveButton);
                positiveBtn = negativeButton;
                negativeButton = null;
            }
            if (this.mIconResId <= 0) {
                titleTxt.setCompoundDrawablePadding(0);
            } else {
                titleTxt.setCompoundDrawablesWithIntrinsicBounds(
                        this.mIconResId, 0, 0, 0);
                titleTxt.setCompoundDrawablePadding((int) (10.0F * this.mContext
                        .getResources().getDisplayMetrics().density));
            }
            if (i == 0) {
                if ((this.mNegativeButtonText != null)
                        && ((this.mPositiveButtonText != null) || (this.mExpendButtonText != null))) {
                    ((View) negativeButton).setVisibility(View.VISIBLE);
                }
                if ((this.mExpendButtonText != null)
                        && (this.mPositiveButtonText != null)) {
                    ((View) negativeButton).setVisibility(View.VISIBLE);
                }
            } else {
                if ((this.mPositiveButtonText != null)
                        && ((this.mNegativeButtonText != null) || (this.mExpendButtonText != null))) {
                    negativeDevider.setVisibility(View.VISIBLE);
                }
                if ((this.mExpendButtonText != null)
                        && (this.mNegativeButtonText != null)) {
                    ((View) negativeButton).setVisibility(View.VISIBLE);
                }
            }
            if (this.mPositiveButtonText == null) {
                ((Button) positiveBtn).setVisibility(View.GONE);
            } else {
                ((Button) positiveBtn).setText(this.mPositiveButtonText);
                ((Button) positiveBtn)
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View paramAnonymousView) {
                                if (CustomDialog.Builder.this.mPositiveButtonClickListener != null) {
                                    CustomDialog.Builder.this.mPositiveButtonClickListener
                                            .onClick(dialog, -1);
                                }
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });
            }
            if (this.mNegativeButtonText == null) {
                negativeBtn.setVisibility(View.GONE);
            } else {
                negativeBtn.setText(this.mNegativeButtonText);
                negativeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        if (CustomDialog.Builder.this.mNegativeButtonClickListener != null) {
                            CustomDialog.Builder.this.mNegativeButtonClickListener
                                    .onClick(dialog, -2);
                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
            }
            if (this.mExpendButtonText == null) {
                localButton1.setVisibility(View.GONE);
            } else {
                localButton1.setText(this.mExpendButtonText);
                localButton1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        if (CustomDialog.Builder.this.mExpendButtonClickListener != null) {
                            CustomDialog.Builder.this.mExpendButtonClickListener
                                    .onClick(dialog, -3);
                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
            }
            if (this.mMessage == null) {
                if (this.contentView != null) {
                    FrameLayout root = ((FrameLayout) view
                            .findViewById(R.id.content));
                    root.removeAllViews();
                    root.addView(this.contentView);
                }
            } else {
                ((TextView) view.findViewById(R.id.message))
                        .setText(this.mMessage);
            }
            dialog.setContentView(view);
            return dialog;
        }

        public Builder setContentView(View paramView) {
            this.contentView = paramView;
            return this;
        }

        public Builder setExpendButton(int paramInt,
                                       OnClickListener paramOnClickListener) {
            return setNegativeButton((String) this.mContext.getText(paramInt),
                    paramOnClickListener);
        }

        public Builder setExpendButton(String paramString,
                                       OnClickListener paramOnClickListener) {
            this.mExpendButtonText = paramString;
            this.mExpendButtonClickListener = paramOnClickListener;
            return this;
        }

        public Builder setMessage(int paramInt) {
            this.mMessage = ((String) this.mContext.getText(paramInt));
            return this;
        }

        public Builder setMessage(String paramString) {
            this.mMessage = paramString;
            return this;
        }

        public Builder setNegativeButton(int paramInt,
                                         OnClickListener paramOnClickListener) {
            return setNegativeButton((String) this.mContext.getText(paramInt),
                    paramOnClickListener);
        }

        public Builder setNegativeButton(String paramString,
                                         OnClickListener paramOnClickListener) {
            this.mNegativeButtonText = paramString;
            this.mNegativeButtonClickListener = paramOnClickListener;
            return this;
        }

        public Builder setPositiveButton(int paramInt,
                                         OnClickListener paramOnClickListener) {
            return setPositiveButton((String) this.mContext.getText(paramInt),
                    paramOnClickListener);
        }

        public Builder setPositiveButton(String paramString,
                                         OnClickListener paramOnClickListener) {
            this.mPositiveButtonText = paramString;
            this.mPositiveButtonClickListener = paramOnClickListener;
            return this;
        }

        public Builder setTitle(int paramInt) {
            this.mTitle = ((String) this.mContext.getText(paramInt));
            return this;
        }

        public Builder setTitle(String paramString) {
            this.mTitle = paramString;
            return this;
        }

        public Builder setTitleIcon(int paramInt) {
            this.mIconResId = paramInt;
            return this;
        }

        public Builder setSingleChoiceItems(String[] datas,
                                            int defaultPosition,
                                            final OnClickListener listener) {
            final ItemsAdapter itemsAdapter = new ItemsAdapter(this.mContext,
                    datas, defaultPosition, listener);
            this.contentView = LayoutInflater.from(this.mContext).inflate(
                    R.layout.dialog_items_layout, null);
            final ListView localListView = (ListView) this.contentView;
            localListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            localListView.setAdapter(itemsAdapter);
            localListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if (listener != null) {
                        listener.onClick(dialog, position);
                    }
                    itemsAdapter.setPosition(position);
                    itemsAdapter.notifyDataSetChanged();
                }

            });
            if (defaultPosition >= 0) {
                localListView.setSelection(defaultPosition);
            }
            return this;
        }

        public Builder setItems(String[] datas, int paramInt,
                                final OnClickListener listener) {
            this.mItemsAdapter = new ItemsAdapter(this.mContext, datas,
                    ItemsAdapter.ItemStyle.ITEM, listener);
            this.contentView = LayoutInflater.from(this.mContext).inflate(
                    R.layout.dialog_items_layout, null);
            ListView localListView = (ListView) this.contentView;
            localListView.setAdapter(this.mItemsAdapter);
            localListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if (listener != null) {
                        listener.onClick(dialog, position);
                    }
                }
            });

            return this;
        }
    }
}
