package com.work.guaishouxingqiu.aboutball.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 17:10
 * 更新时间: 2019/6/13 17:10
 * 描述:社区推荐热门
 */
public class ResultRecommendHotBean implements Parcelable {
    public long topicId;
    public String topicTitle;
    public String imageUrl;
    public String description;

    protected ResultRecommendHotBean(Parcel in) {
        topicId = in.readLong();
        topicTitle = in.readString();
        imageUrl = in.readString();
        description = in.readString();
    }

    public static final Creator<ResultRecommendHotBean> CREATOR = new Creator<ResultRecommendHotBean>() {
        @Override
        public ResultRecommendHotBean createFromParcel(Parcel in) {
            return new ResultRecommendHotBean(in);
        }

        @Override
        public ResultRecommendHotBean[] newArray(int size) {
            return new ResultRecommendHotBean[size];
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
        dest.writeString(description);
    }
}
