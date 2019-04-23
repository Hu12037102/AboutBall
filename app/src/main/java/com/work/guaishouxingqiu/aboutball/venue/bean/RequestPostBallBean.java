package com.work.guaishouxingqiu.aboutball.venue.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/23 14:46
 * 更新时间: 2019/4/23 14:46
 * 描述:发起约球bean
 */
public class RequestPostBallBean implements Parcelable{
public long calendarId;
public long[] refereeId;
public long stadiumId;
public long teamId;

    protected RequestPostBallBean(Parcel in) {
        calendarId = in.readLong();
        refereeId = in.createLongArray();
        stadiumId = in.readLong();
        teamId = in.readLong();
    }

    public static final Creator<RequestPostBallBean> CREATOR = new Creator<RequestPostBallBean>() {
        @Override
        public RequestPostBallBean createFromParcel(Parcel in) {
            return new RequestPostBallBean(in);
        }

        @Override
        public RequestPostBallBean[] newArray(int size) {
            return new RequestPostBallBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(calendarId);
        dest.writeLongArray(refereeId);
        dest.writeLong(stadiumId);
        dest.writeLong(teamId);
    }
}
