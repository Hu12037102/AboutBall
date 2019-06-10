package com.work.guaishouxingqiu.aboutball.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 13:07
 * 更新时间: 2019/5/9 13:07
 * 描述:返回裁判记录bean
 */
public class ResultRefereeRecordBean implements Parcelable {
    public long agreeId;
    public String stadiumName;
    public String startTime;
    public String endTime;
    public String hostTeamName;
    public long hostTeamId;
    public long guestTeamId;
    public String guestTeamName;

    protected ResultRefereeRecordBean(Parcel in) {
        agreeId = in.readLong();
        stadiumName = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        hostTeamName = in.readString();
        hostTeamId = in.readLong();
        guestTeamId = in.readLong();
        guestTeamName = in.readString();
    }

    public static final Creator<ResultRefereeRecordBean> CREATOR = new Creator<ResultRefereeRecordBean>() {
        @Override
        public ResultRefereeRecordBean createFromParcel(Parcel in) {
            return new ResultRefereeRecordBean(in);
        }

        @Override
        public ResultRefereeRecordBean[] newArray(int size) {
            return new ResultRefereeRecordBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(agreeId);
        dest.writeString(stadiumName);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(hostTeamName);
        dest.writeLong(hostTeamId);
        dest.writeLong(guestTeamId);
        dest.writeString(guestTeamName);
    }
}
