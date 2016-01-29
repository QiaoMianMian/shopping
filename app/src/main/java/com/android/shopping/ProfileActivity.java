package com.android.shopping;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shopping.utils.PreferenceUtils;
import com.android.shopping.utils.SystemUtils;
import com.android.shopping.widget.photo.DisplayImageOptionManager;

/**
 * Created by Administrator on 2016/1/22.
 */
public class ProfileActivity extends Activity implements View.OnClickListener {
    private ImageView profileBack;
    private TextView profileSave;
    private RelativeLayout profileAvatarLayout;
    private ImageView profileAvatar;
    private LinearLayout profileIdLayout;
    private TextView profileId;
    private LinearLayout profileNameLayout;
    private EditText profileName;
    private LinearLayout profileNumberLayout;
    private EditText profileNumber;
    private RelativeLayout profileGenderLayout;
    private ImageView profileGender;
    private boolean isFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        initViewsDatas();
    }

    private void initViews() {
        profileBack = (ImageView) this.findViewById(R.id.profileBack);
        profileBack.setOnClickListener(this);
        profileSave = (TextView) this.findViewById(R.id.profileSave);
        profileSave.setOnClickListener(this);
        profileAvatarLayout = (RelativeLayout) this.findViewById(R.id.profileAvatarLayout);
        profileAvatar = (ImageView) this.findViewById(R.id.profileAvatar);
        profileIdLayout = (LinearLayout) this.findViewById(R.id.profileIdLayout);
        profileId = (TextView) this.findViewById(R.id.profileId);
        profileNameLayout = (LinearLayout) this.findViewById(R.id.profileNameLayout);
        profileName = (EditText) this.findViewById(R.id.profileName);
        profileNumberLayout = (LinearLayout) this.findViewById(R.id.profileNumberLayout);
        profileNumber = (EditText) this.findViewById(R.id.profileNumber);
        profileGenderLayout = (RelativeLayout) this.findViewById(R.id.profileGenderLayout);
        profileGenderLayout.setOnClickListener(this);
        profileGender = (ImageView) this.findViewById(R.id.profileGender);
    }

    private void initViewsDatas() {
        String userAvatarLocal = PreferenceUtils.getPrefString(this, PreferenceUtils.PRF_AVATAR_URI, "");
        String userAvatarUri = PreferenceUtils.getPrefString(this, PreferenceUtils.PRF_AVATAR_URI, "");
        if (profileAvatar != null) {
            String url;
            if (TextUtils.isEmpty(userAvatarUri)) {
                url = userAvatarUri;
            } else {
                url = "file://" + userAvatarLocal;
            }
            setImgAvatar(this, url, profileAvatar);
        }
        profileId.setText(SystemUtils.getIp(this));
        String userName = PreferenceUtils.getPrefString(this, PreferenceUtils.PRF_LOGIN_USER, "");
        profileName.setText(userName);
        String userPhone = PreferenceUtils.getPrefString(this, PreferenceUtils.PRF_USER_PHONE, "");
        profileNumber.setText(userPhone);
        isFemale = PreferenceUtils.getPrefBoolean(this, PreferenceUtils.PRF_USER_GENDER, false);
        if (isFemale) {
            profileGender.setImageResource(R.mipmap.ic_gender_female);
        } else {
            profileGender.setImageResource(R.mipmap.ic_gender_male);
        }
    }

    protected void setImgAvatar(Context context, String path, ImageView mImgAvatar) {
        if (TextUtils.isEmpty(path) || (mImgAvatar == null)) {
            return;
        }
        ((App) context.getApplicationContext()).getImageLoader()
                .displayImage(path, mImgAvatar,
                        DisplayImageOptionManager.getDisplayImageOption(context,
                                DisplayImageOptionManager.ImageOptionStyle.ROUND_AVATAR_NO_CACHE));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileBack:
                finish();
                break;
            case R.id.profileSave:
                save();
                finish();
                break;
            case R.id.profileGenderLayout:
                if (isFemale) {//女
                    isFemale = false;
                    profileGender.setImageResource(R.mipmap.ic_gender_male);
                    PreferenceUtils.setPrefBoolean(this, PreferenceUtils.PRF_USER_GENDER, false);
                } else {
                    isFemale = true;
                    profileGender.setImageResource(R.mipmap.ic_gender_female);
                    PreferenceUtils.setPrefBoolean(this, PreferenceUtils.PRF_USER_GENDER, true);
                }
                break;
        }
    }

    private void save() {
        String name = profileName.getText().toString();
        PreferenceUtils.setPrefString(this, PreferenceUtils.PRF_LOGIN_USER, name);
        String phone = profileNumber.getText().toString();
        PreferenceUtils.setPrefString(this, PreferenceUtils.PRF_USER_PHONE, phone);
    }
}
