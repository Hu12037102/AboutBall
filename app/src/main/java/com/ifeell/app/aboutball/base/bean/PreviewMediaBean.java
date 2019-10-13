package com.ifeell.app.aboutball.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 媒体库资源预览类
 */
public class PreviewMediaBean implements Parcelable {
    public static final int MEDIA_TYPW_IMAGE =0;//图片类型
    public static final int MEDIA_TYPE_VIDEO =1;//视频类型
    public String mediaUrl;
    public int mediaType;


    protected PreviewMediaBean(Parcel in) {
        mediaUrl = in.readString();
        mediaType = in.readInt();
    }

    public static final Creator<PreviewMediaBean> CREATOR = new Creator<PreviewMediaBean>() {
        @Override
        public PreviewMediaBean createFromParcel(Parcel in) {
            return new PreviewMediaBean(in);
        }

        @Override
        public PreviewMediaBean[] newArray(int size) {
            return new PreviewMediaBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mediaUrl);
        dest.writeInt(mediaType);
    }
}
