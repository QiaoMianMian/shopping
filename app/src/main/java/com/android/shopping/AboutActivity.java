package com.android.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.shopping.utils.ToastUtils;

/**
 * Created by Administrator on 2016/1/22.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initViews();
        hideHeader();
    }

    private void initViews() {
        this.findViewById(R.id.aboutBack).setOnClickListener(this);
        this.findViewById(R.id.aboutVersionLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutBack:
                finish();
                break;
            case R.id.aboutVersionLayout:
                ToastUtils.showToast(this, "You have the latest version installed.");
                break;
        }
    }
}
