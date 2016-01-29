package com.android.shopping;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.shopping.utils.IntentUtils;
import com.android.shopping.utils.ToastUtils;
import com.android.shopping.widget.photo.CropImage;
import com.android.shopping.widget.photo.CropImageView;
import com.android.shopping.widget.photo.HighlightView;


/**
 * The activity can crop specific region of interest from an image.
 */
public class AvatarUploadActivity extends BaseActivity {
    final int IMAGE_MAX_SIZE = 1024;
    public static final int NO_STORAGE_ERROR = -1;
    public static final int CANNOT_STAT_ERROR = -2;
    protected static final int MSG_REQUEST_SUCCESS = 0x00001;
    protected static final int MSG_REQUEST_FAILED = 0x00002;

    private static final String TAG = "CropImage";
    public static final String EXTRA_UPLOAD_TYPE = "upload_type";
    public static final String IMAGE_PATH = "image-path";
    public static final String SCALE = "scale";
    public static final String ORIENTATION_IN_DEGREES = "orientation_in_degrees";
    public static final String ASPECT_X = "aspectX";
    public static final String ASPECT_Y = "aspectY";
    public static final String OUTPUT_X = "outputX";
    public static final String OUTPUT_Y = "outputY";
    public static final String SCALE_UP_IF_NEEDED = "scaleUpIfNeeded";
    public static final String CIRCLE_CROP = "circleCrop";
    public static final String RETURN_DATA = "return-data";
    public static final String RETURN_DATA_AS_BITMAP = "data";
    public static final String ACTION_INLINE_DATA = "inline-data";

    // These are various options can be specified in the intent.
    private Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;
    private Uri mSaveUri = null;
    private boolean mDoFaceDetection = true;
    private boolean mCircleCrop = false;

    private int mAspectX;
    private int mAspectY;
    private int mOutputX;
    private int mOutputY;
    private boolean mScale;
    private CropImageView mImageView;
    private ContentResolver mContentResolver;
    private Bitmap mBitmap;
    private String mImagePath;
    private String mSavedpath;

    boolean mWaitingToPick; // Whether we are wait the user to pick a face.
    boolean mSaving; // Whether the "save" button is already clicked.
    HighlightView mCrop;
    private boolean mScaleUp = true;
    private CropImage mCropImg;
    private String mUploadType;
    private int mResultCode = -2;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_avatar_upload);
        // enableTitleBar();
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContentResolver = getContentResolver();
        mImageView = (CropImageView) findViewById(R.id.image);
        showStorageToast(this);
        Intent intent = getIntent();
        mImagePath = intent.getStringExtra(IMAGE_PATH);
        mUploadType = intent.getStringExtra(EXTRA_UPLOAD_TYPE);
        if (mImagePath == null) {
            this.finish();
            return;
        }
        try {
            mCropImg = new CropImage(this, mImageView, mImagePath, true);
        } catch (Exception e1) {
            Toast.makeText(this, "no picture", Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
            this.finish();
            return;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCropImg.startCrop();
            }
        }, 10);

        findViewById(R.id.btn_confirm).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            mSavedpath = mCropImg.saveToLocal();
                            submit();
                        } catch (Exception e) {
                            finish();
                        }
                    }
                });

        findViewById(R.id.rotateRight).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mCropImg.startRotate(90);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBitmap != null) {
            mBitmap.recycle();
        }
    }

    public static void showStorageToast(Activity activity) {
        showStorageToast(activity, calculatePicturesRemaining(activity));
    }

    public static void showStorageToast(Activity activity, int remaining) {

        String noStorageText = null;

        if (remaining == NO_STORAGE_ERROR) {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_CHECKING)) {
                noStorageText = activity.getString(R.string.preparing_card);
            } else {
                noStorageText = activity.getString(R.string.no_storage_card);
            }
        } else if (remaining < 1) {
            noStorageText = activity.getString(R.string.not_enough_space);
        }
        if (noStorageText != null) {
            Toast.makeText(activity, noStorageText, Toast.LENGTH_SHORT).show();
        }
    }

    public static int calculatePicturesRemaining(Activity activity) {
        try {
            String storageDirectory = "";
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                storageDirectory = Environment.getExternalStorageDirectory().toString();
            } else {
                storageDirectory = activity.getFilesDir().toString();
            }
            StatFs stat = new StatFs(storageDirectory);
            float remaining = ((float) stat.getAvailableBlocks() * (float) stat.getBlockSize()) / 400000F;
            return (int) remaining;
        } catch (Exception ex) {
            return CANNOT_STAT_ERROR;
        }
    }

    private void submit() {
        if (!TextUtils.isEmpty(mSavedpath)) {
            Message msg = Message.obtain();
            msg.what = MSG_REQUEST_SUCCESS;
            msg.obj = mSavedpath;
            mHandler.sendMessage(msg);
        } else {
            ToastUtils.showToast(this, "error!");
            this.finish();
        }
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_REQUEST_SUCCESS) {
                mResultCode = RESULT_OK;
                Intent intent = new Intent();
                intent.putExtra(IntentUtils.EXTRA_PHOTO_URL, (String) msg.obj);
                setResult(mResultCode, intent);
            } else if (msg.what == MSG_REQUEST_FAILED) {
                ToastUtils.showToast(AvatarUploadActivity.this, "Error!");
                setResult(mResultCode);
            }
            finish();
        }
    };

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
