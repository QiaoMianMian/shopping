package com.android.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/12/28.
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_layout);
    }

    @Override
    public void setContentView(int layoutResID) {
        ViewGroup root = (ViewGroup) findViewById(R.id.base_content_layout);
        getLayoutInflater().inflate(layoutResID, root);
    }

    public void hideHeader() {
        findViewById(R.id.layout_title_root).setVisibility(View.GONE);
    }

    public void hideLeftText() {
        findViewById(R.id.head_txt_left).setVisibility(View.GONE);
    }

    public void hideCenterText() {
        findViewById(R.id.head_txt_center).setVisibility(View.GONE);
    }

    public void hideRightIv(boolean flag) {
        findViewById(R.id.head_iv_right).setVisibility(View.GONE);
    }

    public void goAction(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        this.startActivity(intent);
    }
}
