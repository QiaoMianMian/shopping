package com.android.shopping.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shopping.AboutActivity;
import com.android.shopping.AddressActivity;
import com.android.shopping.App;
import com.android.shopping.AvatarUploadActivity;
import com.android.shopping.Contants;
import com.android.shopping.LoginActivity;
import com.android.shopping.ProfileActivity;
import com.android.shopping.R;
import com.android.shopping.utils.IntentUtils;
import com.android.shopping.utils.Logger;
import com.android.shopping.utils.PreferenceUtils;
import com.android.shopping.utils.StatisticsUtils;
import com.android.shopping.utils.StreamUtils;
import com.android.shopping.utils.TapjoyEx;
import com.android.shopping.widget.CustomDialog;
import com.android.shopping.widget.photo.DisplayImageOptionManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/12/29.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private String userName;
    private String userPoints;
    private String userAvatarUri; // facebook
    private String userAvatarLocal; //本地头像
    private TextView txtMeName;
    private TextView layoutMePoints;
    private ImageView txtMeTopup;
    private ImageView imgAvatar;
    private ImageView imgCamera;
    private TextView layoutMeLogin;
    private LinearLayout layoutProfileMeItem;
    private LinearLayout layoutRecordMeItem;
    private LinearLayout layoutAddressMeItem;
    private LinearLayout layoutPointsMeItem;
    private LinearLayout layoutAboutMeItem;
    private LinearLayout layoutExitMeItem;

    private File mFileTemp;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_meet_photo.jpg";
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Contants.BR_GET_POINTS.equalsIgnoreCase(action)) {
                if (layoutMePoints != null) {
                    userPoints = "" + PreferenceUtils.getPrefInt(getActivity(), PreferenceUtils.PRF_USER_POINTS, 0);
                    layoutMePoints.setText(userPoints);
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFilter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setViewDatas();
        StatisticsUtils.onResume(getActivity());
        StatisticsUtils.event(getActivity(), StatisticsUtils.SS_FRG_PERSON);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatisticsUtils.onPause(getActivity());
    }

    private void setViewDatas() {
        userName = PreferenceUtils.getPrefString(getActivity(), PreferenceUtils.PRF_LOGIN_USER, "");
        userPoints = "" + PreferenceUtils.getPrefInt(getActivity(), PreferenceUtils.PRF_USER_POINTS, 0);
        userAvatarUri = PreferenceUtils.getPrefString(getActivity(), PreferenceUtils.PRF_AVATAR_URI, "");
        userAvatarLocal = PreferenceUtils.getPrefString(getActivity(), PreferenceUtils.PRF_AVATAR_URI, "");
        if (txtMeName != null) {
            txtMeName.setText(userName);
        }
        if (layoutMePoints != null) {
            layoutMePoints.setText(userPoints);
        }
        if (imgAvatar != null) {
            String url;
            if (TextUtils.isEmpty(userAvatarUri)) {
                url = userAvatarUri;
            } else {
                url = "file://" + userAvatarLocal;
            }
            setImgAvatar(getActivity(), url, imgAvatar);
        }

        if (TextUtils.isEmpty(userName)) {
            layoutMeLogin.setVisibility(View.VISIBLE);
        }
    }

    private void initFilter() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Contants.BR_GET_POINTS);
        getActivity().registerReceiver(mReceiver, mFilter);
    }

    private void initViews(View view) {
        layoutMeLogin = (TextView) view.findViewById(R.id.layoutMeLogin);
        layoutMeLogin.setOnClickListener(this);
        txtMeName = (TextView) view.findViewById(R.id.txtMeName);
        layoutMePoints = (TextView) view.findViewById(R.id.layoutMePoints);
        txtMeTopup = (ImageView) view.findViewById(R.id.txtMeTopup);
        txtMeTopup.setOnClickListener(this);
        imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(this);
        imgCamera = (ImageView) view.findViewById(R.id.imgCamera);
        imgCamera.setOnClickListener(this);
        layoutProfileMeItem = (LinearLayout) view.findViewById(R.id.layoutProfileMeItem);
        layoutProfileMeItem.setOnClickListener(this);
        layoutRecordMeItem = (LinearLayout) view.findViewById(R.id.layoutRecordMeItem);
        layoutRecordMeItem.setOnClickListener(this);
        layoutAddressMeItem = (LinearLayout) view.findViewById(R.id.layoutAddressMeItem);
        layoutAddressMeItem.setOnClickListener(this);
        layoutPointsMeItem = (LinearLayout) view.findViewById(R.id.layoutPointsMeItem);
        layoutPointsMeItem.setOnClickListener(this);
        layoutAboutMeItem = (LinearLayout) view.findViewById(R.id.layoutAboutMeItem);
        layoutAboutMeItem.setOnClickListener(this);
        layoutExitMeItem = (LinearLayout) view.findViewById(R.id.layoutExitMeItem);
        layoutExitMeItem.setOnClickListener(this);

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutMeLogin:
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_PERSON_LOGIN);
                Intent login = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(login);
                getActivity().finish();
                break;
            case R.id.imgAvatar:
            case R.id.imgCamera:
                showTakePhoto();
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_PERSON_AVATAR);
                break;
            case R.id.txtMeTopup:
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_PERSON_TOP);
                break;
            case R.id.layoutProfileMeItem:
                goAction(ProfileActivity.class);
                break;
            case R.id.layoutRecordMeItem:
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_PERSON_RECORD);
                break;
            case R.id.layoutAddressMeItem:
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_PERSON_ADDRESS);
                Intent address = new Intent(getActivity(), AddressActivity.class);
                getActivity().startActivity(address);
                break;
            case R.id.layoutPointsMeItem:
                TapjoyEx.offerWallTapjoy(getActivity());
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_PERSON_POINTS);
                break;
            case R.id.layoutAboutMeItem:
                Intent about = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(about);
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_PERSON_ABOUT);
                break;
            case R.id.layoutExitMeItem:
                StatisticsUtils.event(getActivity(), StatisticsUtils.SS_PERSON_EXIT);
                PreferenceUtils.setPrefBoolean(getActivity(), PreferenceUtils.PRF_LOGIN_EXIT, true);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
        }
    }

    private void goAction(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (REQUEST_CODE_TAKE_PICTURE == requestCode) {
                startCropImage();
            } else if (REQUEST_CODE_GALLERY == requestCode) {
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    StreamUtils.copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    startCropImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (REQUEST_CODE_CROP_IMAGE == requestCode) {
                String photoUrl = data.getStringExtra(IntentUtils.EXTRA_PHOTO_URL);
                PreferenceUtils.setPrefString(getActivity(), PreferenceUtils.PRF_AVATAR_URI, photoUrl);
                setImgAvatar(getActivity(), photoUrl, imgAvatar);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startCropImage() {
        Intent intent = new Intent(getActivity(), AvatarUploadActivity.class);
        intent.putExtra(AvatarUploadActivity.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(AvatarUploadActivity.EXTRA_UPLOAD_TYPE, Contants.TYPE_UPLOAD_AVATART_PHOTO);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    private void showTakePhoto() {
        new CustomDialog.Builder(getActivity())
                .setTitle(R.string.txt_choice_mode)
                .setTitleIcon(0)
                .setItems(getResources().getStringArray(R.array.take_photo),
                        -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (which == 0) {
                                    takePicture();
                                } else if (which == 1) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    intent.addCategory("android.intent.category.OPENABLE");
                                    startActivityForResult(Intent.createChooser(intent, "Save"),
                                            REQUEST_CODE_GALLERY);
                                }
                            }
                        }).create().show();
    }

    private void takePicture() {
        if (mFileTemp.exists()) {
            mFileTemp.delete();
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFileTemp));
        intent.putExtra("android.intent.extra.videoQuality", 1);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
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
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}
