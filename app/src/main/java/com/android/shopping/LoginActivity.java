package com.android.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.shopping.utils.Logger;
import com.android.shopping.utils.PreferenceUtils;
import com.android.shopping.utils.StatisticsUtils;
import com.android.shopping.utils.ToastUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

/**
 * Created by Administrator on 2015/12/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText editLoginAccount;
    private EditText editLoginPwd;
    private CheckBox checkRemeberpwd;
    private Button btnRegister;
    private Button btnLogin;

    private String loginUser;
    private String loginPwd;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFb();
        setContentView(R.layout.activity_login);
        initDatas();
        initViews();
        boolean exit = PreferenceUtils.getPrefBoolean(this, PreferenceUtils.PRF_LOGIN_EXIT, false);
        if (!TextUtils.isEmpty(loginUser) && !TextUtils.isEmpty(loginPwd) && !exit) {
            goAction(MainActivity.class);
            finish();
        }
    }

    public void initFb() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Profile profile = Profile.getCurrentProfile();
                        Logger.i("YWS", "Id:" + profile.getId());
                        Logger.i("YWS", "Name::" + profile.getName());
                        Logger.i("YWS", "LinkUri:" + profile.getLinkUri());
                        Logger.i("YWS", "PictureUri:" + profile.getProfilePictureUri(500, 500));
                        Logger.i("YWS", "Describe:" + profile.describeContents());
                        Logger.e("YWS", "Success:" + loginResult.getAccessToken());
                        PreferenceUtils.setPrefString(LoginActivity.this, PreferenceUtils.PRF_LOGIN_USER,
                                profile.getName());
                        PreferenceUtils.setPrefString(LoginActivity.this, PreferenceUtils.PRF_AVATAR_URI,
                                profile.getProfilePictureUri(500, 500).toString());
                        goAction(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("YWS", "onCancel:");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("YWS", "onError:");
                    }

                });

    }

    public void initDatas() {
        loginUser = PreferenceUtils.getPrefString(this, PreferenceUtils.PRF_LOGIN_USER, "");
        loginPwd = PreferenceUtils.getPrefString(this, PreferenceUtils.PRF_LOGIN_PWD, "");
    }

    public void initViews() {
        editLoginAccount = (EditText) this.findViewById(R.id.editLoginAccount);
        editLoginPwd = (EditText) this.findViewById(R.id.editLoginPwd);
        checkRemeberpwd = (CheckBox) this.findViewById(R.id.checkRemeberpwd);
        checkRemeberpwd.setOnClickListener(this);
        btnRegister = (Button) this.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        boolean remeber = PreferenceUtils.getPrefBoolean(this, PreferenceUtils.PRF_REMEBER_PWD, true);
        if (remeber) {
            editLoginAccount.setText(loginUser);
            editLoginPwd.setText(loginPwd);
        }
        checkRemeberpwd.setChecked(remeber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        PreferenceUtils.setPrefBoolean(this, PreferenceUtils.PRF_LOGIN_EXIT, false);
        String editUser = editLoginAccount.getText().toString();
        String editPwd = editLoginPwd.getText().toString();
        switch (v.getId()) {
            case R.id.checkRemeberpwd:
                StatisticsUtils.event(this, StatisticsUtils.SS_REMEBER);
                PreferenceUtils.setPrefBoolean(this, PreferenceUtils.PRF_REMEBER_PWD, checkRemeberpwd.isChecked());
                break;
            case R.id.btnRegister:
                StatisticsUtils.event(this, StatisticsUtils.SS_REGISTER);
                PreferenceUtils.setPrefString(this, PreferenceUtils.PRF_AVATAR_URI, "");
                if (TextUtils.isEmpty(editUser)) {
                    ToastUtils.show(this, R.string.input_user);
                    break;
                } else if (editUser.equals(loginUser)) {
                    ToastUtils.show(this, R.string.user_used);
                    break;
                } else {
                    PreferenceUtils.setPrefString(this, PreferenceUtils.PRF_LOGIN_USER, editUser);
                }
                if (TextUtils.isEmpty(editPwd)) {
                    ToastUtils.show(this, R.string.input_pwd);
                    break;
                } else {
                    PreferenceUtils.setPrefString(this, PreferenceUtils.PRF_LOGIN_PWD, editPwd);
                }
                goAction(MainActivity.class);
                finish();
                break;
            case R.id.btnLogin:
                StatisticsUtils.event(this, StatisticsUtils.SS_LOGNI);
                if (TextUtils.isEmpty(editUser)) {
                    ToastUtils.show(this, R.string.input_user);
                    break;
                }
                if (TextUtils.isEmpty(editPwd)) {
                    ToastUtils.show(this, R.string.input_pwd);
                    break;
                }
                if (editUser.equals(loginUser) && editPwd.equals(loginPwd)) {
                    goAction(MainActivity.class);
                    finish();
                } else {
                    ToastUtils.show(this, R.string.pwd_errot);
                }
                break;
        }
    }
}
