package com.work.guaishouxingqiu.aboutball.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 17:57
 * 更新时间: 2019/4/4 17:57
 * 描述:
 */
public class ResultUpdateApkBean implements Parcelable {
    public int versionId;
    public String phoneModel;
    public String content;
    public String updateUrl;
    public String version;
    public String isForce;

    protected ResultUpdateApkBean(Parcel in) {
        versionId = in.readInt();
        phoneModel = in.readString();
        content = in.readString();
        updateUrl = in.readString();
        version = in.readString();
        isForce = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(versionId);
        dest.writeString(phoneModel);
        dest.writeString(content);
        dest.writeString(updateUrl);
        dest.writeString(version);
        dest.writeString(isForce);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultUpdateApkBean> CREATOR = new Creator<ResultUpdateApkBean>() {
        @Override
        public ResultUpdateApkBean createFromParcel(Parcel in) {
            return new ResultUpdateApkBean(in);
        }

        @Override
        public ResultUpdateApkBean[] newArray(int size) {
            return new ResultUpdateApkBean[size];
        }
    };
}
