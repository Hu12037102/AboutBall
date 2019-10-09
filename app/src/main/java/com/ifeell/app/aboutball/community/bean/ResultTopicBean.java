package com.ifeell.app.aboutball.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 18:36
 * 更新时间: 2019/6/14 18:36
 * 描述:话题列表bean
 */
public class ResultTopicBean implements Parcelable{
    public long topicId;
    public String topicTitle;
    public String imageUrl;

    protected ResultTopicBean(Parcel in) {
        topicId = in.readLong();
        topicTitle = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<ResultTopicBean> CREATOR = new Creator<ResultTopicBean>() {
        @Override
        public ResultTopicBean createFromParcel(Parcel in) {
            return new ResultTopicBean(in);
        }

        @Override
        public ResultTopicBean[] newArray(int size) {
            return new ResultTopicBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(topicId);
        dest.writeString(topicTitle);
        dest.writeString(imageUrl);
    }
}
