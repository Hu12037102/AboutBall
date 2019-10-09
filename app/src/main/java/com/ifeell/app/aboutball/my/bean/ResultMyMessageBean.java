package com.ifeell.app.aboutball.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/31 15:20
 * 更新时间: 2019/7/31 15:20
 * 描述:我的消息bean
 */
public class ResultMyMessageBean implements Parcelable {
    public int noticeType;//"noticeType": "通知类型 1.订单通知 2.约球通知 3.抽奖通知 4.社区通知 5.观赛通知",
    public String noticeTypeName;
    public String noticeTitle;
    public String createTime;

    protected ResultMyMessageBean(Parcel in) {
        noticeType = in.readInt();
        noticeTypeName = in.readString();
        noticeTitle = in.readString();
        createTime = in.readString();
    }

    public static final Creator<ResultMyMessageBean> CREATOR = new Creator<ResultMyMessageBean>() {
        @Override
        public ResultMyMessageBean createFromParcel(Parcel in) {
            return new ResultMyMessageBean(in);
        }

        @Override
        public ResultMyMessageBean[] newArray(int size) {
            return new ResultMyMessageBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noticeType);
        dest.writeString(noticeTypeName);
        dest.writeString(noticeTitle);
        dest.writeString(createTime);
    }
}
