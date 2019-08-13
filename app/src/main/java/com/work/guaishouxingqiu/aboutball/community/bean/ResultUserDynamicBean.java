package com.work.guaishouxingqiu.aboutball.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/29 14:41
 * 更新时间: 2019/7/29 14:41
 * 描述:用户动态信息bean
 */
public class ResultUserDynamicBean implements Parcelable {
    public String headerImg;
    public int gender;
    public String nickName;
    public String refereeLevel;
    public int followCount;
    public int fansCount;
    public int isFollow;
    public List<ResultCommunityDataBean>tweetList;

    protected ResultUserDynamicBean(Parcel in) {
        headerImg = in.readString();
        gender = in.readInt();
        nickName = in.readString();
        refereeLevel = in.readString();
        followCount = in.readInt();
        fansCount = in.readInt();
        isFollow = in.readInt();
        tweetList = in.createTypedArrayList(ResultCommunityDataBean.CREATOR);
    }

    public static final Creator<ResultUserDynamicBean> CREATOR = new Creator<ResultUserDynamicBean>() {
        @Override
        public ResultUserDynamicBean createFromParcel(Parcel in) {
            return new ResultUserDynamicBean(in);
        }

        @Override
        public ResultUserDynamicBean[] newArray(int size) {
            return new ResultUserDynamicBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(headerImg);
        dest.writeInt(gender);
        dest.writeString(nickName);
        dest.writeString(refereeLevel);
        dest.writeInt(followCount);
        dest.writeInt(fansCount);
        dest.writeTypedList(tweetList);
        dest.writeInt(isFollow);
    }
}
