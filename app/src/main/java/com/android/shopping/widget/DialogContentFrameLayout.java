package com.android.shopping.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.android.shopping.R;


public class DialogContentFrameLayout extends FrameLayout {
    private int mMaxHeight = 0;

    public DialogContentFrameLayout(Context paramContext) {
        super(paramContext);
        init(paramContext);
    }

    public DialogContentFrameLayout(Context paramContext,
                                    AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    public DialogContentFrameLayout(Context paramContext,
                                    AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext);
    }

    private void init(Context paramContext) {
        Resources localResources = paramContext.getResources();
        int j = localResources.getDisplayMetrics().heightPixels;
        int k = localResources.getDimensionPixelSize(R.dimen.dialog_title_text_height);
        int i = localResources.getDimensionPixelSize(R.dimen.dialog_min_margin);
        int m = localResources.getDimensionPixelSize(R.dimen.dialog_title_text_height);
        this.mMaxHeight = (j - k - m - i);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        int i = getMeasuredHeight();
        int j = MeasureSpec.getSize(paramInt1);
        if (i <= this.mMaxHeight) {
            setMeasuredDimension(j, i);
        } else {
            setMeasuredDimension(j, this.mMaxHeight);
        }
    }
}
