package com.work.guaishouxingqiu.aboutball.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 媒体库资源预览类
 */
public class PreviewMediaBean implements Parcelable {
    public String mediaUrl;
    public boolean isVideo;

    protected PreviewMediaBean(Parcel in) {
        mediaUrl = in.readString();
        isVideo = in.readByte() != 0;
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
        dest.writeByte((byte) (isVideo ? 1 : 0));
    }
}
