package com.android.shopping;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.shopping.utils.PreferenceUtils;
import com.android.shopping.utils.ToastUtils;

/**
 * Created by Administrator on 2016/1/13.
 */
public class AddressActivity extends Activity implements View.OnClickListener {
    private EditText mEditText;
    private TextView mBack;
    private TextView mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initViews();
    }

    private void initViews() {
        mEditText = (EditText) this.findViewById(R.id.addr_edit);
        mBack = (TextView) this.findViewById(R.id.addr_back);
        mBack.setOnClickListener(this);
        mSave = (TextView) this.findViewById(R.id.addr_save);
        mSave.setOnClickListener(this);
        String txt = PreferenceUtils.getPrefString(this, PreferenceUtils.PRF_USER_ADDRESS, "");
        mEditText.setText(txt);
    }


    @Override
    public void onClick(View v) {
        String txt = mEditText.getText().toString();
        switch (v.getId()) {
            case R.id.addr_back:
            case R.id.addr_save:
                saveTxt(txt);
                break;
        }
    }

    private void saveTxt(String txt) {
        if (!TextUtils.isEmpty(txt)) {
            PreferenceUtils.setPrefString(this, PreferenceUtils.PRF_USER_ADDRESS, txt);
            finish();
        } else {
            ToastUtils.showToast(this, R.string.input_address);
        }
    }
}
