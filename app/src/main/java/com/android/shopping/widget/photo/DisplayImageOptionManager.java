package com.android.shopping.widget.photo;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.shopping.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class DisplayImageOptionManager {
    public static DisplayImageOptions getDisplayImageOption(Context context, ImageOptionStyle style) {
        int cornor = context.getResources().getDimensionPixelSize(R.dimen.avatar_radius);
        int cornor90 = context.getResources().getDimensionPixelSize(R.dimen.avatar_radius_90);
        switch (style) {
            case ROUND_AVATAR_MALE:
                return new DisplayImageOptions.Builder()
                        .showStubImage(R.mipmap.ic_male_1)
                        .showImageForEmptyUri(R.mipmap.ic_male_1)
                        .showImageOnFail(R.mipmap.ic_male_1)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .imageScaleType(ImageScaleType.NONE).cacheInMemory(true)
                        .cacheOnDisk(true)
                                //.resetViewBeforeLoading(true)
                        .displayer(new RoundedBitmapDisplayer(cornor)).build();
            case ROUND_AVATAR_FEMALE:
                return new DisplayImageOptions.Builder()
                        .showStubImage(R.mipmap.ic_female_1)
                        .showImageForEmptyUri(R.mipmap.ic_female_1)
                        .showImageOnFail(R.mipmap.ic_female_1)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true)
                        .cacheOnDisk(true)
                                //.resetViewBeforeLoading(true)
                        .displayer(new RoundedBitmapDisplayer(cornor)).build();
            case ROUND_AVATAR_MALE_90:
                return new DisplayImageOptions.Builder()
                        .showStubImage(R.mipmap.ic_male_1)
                        .showImageForEmptyUri(R.mipmap.ic_male_1)
                        .showImageOnFail(R.mipmap.ic_male_1)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .imageScaleType(ImageScaleType.NONE).cacheInMemory(true)
                        .cacheOnDisk(true)
                                //.resetViewBeforeLoading(true)
                        .displayer(new RoundedBitmapDisplayer(cornor90)).build();
            case ROUND_AVATAR_FEMALE_90:
                return new DisplayImageOptions.Builder()
                        .showStubImage(R.mipmap.ic_male_1)
                        .showImageForEmptyUri(R.mipmap.ic_male_1)
                        .showImageOnFail(R.mipmap.ic_male_1)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true)
                        .cacheOnDisk(true)
                                //.resetViewBeforeLoading(true)
                        .displayer(new RoundedBitmapDisplayer(cornor90)).build();
            case AVATAR_LARGE:
                return new DisplayImageOptions.Builder()
                        .showImageForEmptyUri(R.mipmap.ic_male_1)
                        .showImageOnFail(R.mipmap.ic_male_1)
                                //.resetViewBeforeLoading()
                        .cacheOnDisk(true)
                        .cacheInMemory(true).imageScaleType(ImageScaleType.NONE)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
            case ROUND_AVATAR_NO_CACHE:
                return new DisplayImageOptions.Builder()
                        .showStubImage(R.mipmap.ic_male_1)
                        .showImageForEmptyUri(R.mipmap.ic_male_1)
                        .showImageOnFail(R.mipmap.ic_male_1)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(false)
                        .cacheOnDisk(false)
                        .displayer(new RoundedBitmapDisplayer(cornor90)).build();
            default:
                return null;
        }
    }

    public enum ImageOptionStyle {
        ROUND_AVATAR_MALE, ROUND_AVATAR_FEMALE, ROUND_AVATAR_MALE_90, ROUND_AVATAR_FEMALE_90, AVATAR_LARGE, ROUND_AVATAR_NO_CACHE
    }
}
