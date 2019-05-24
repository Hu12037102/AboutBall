package com.work.guaishouxingqiu.aboutball.venue.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/16 17:39
 * 更新时间: 2019/5/16 17:39
 * 描述:约球详情bean
 */
public class ResultAboutBallDetailsBean implements Parcelable{
    public long offerId;
    public long stadiumId;
    public long areaId;
    public long calendarId;
    public String stadiumName;
    public long hostTeamId;
    public long guestTeamId;
    public String hostTeamName;
    public String hostTeamLogo;
    public String guestTeamLogo;
    public String startTime;
    public String endTime;
    public double cost;
    public String guestTeamName;

    protected ResultAboutBallDetailsBean(Parcel in) {
        offerId = in.readLong();
        stadiumId = in.readLong();
        areaId = in.readLong();
        calendarId = in.readLong();
        stadiumName = in.readString();
        hostTeamId = in.readLong();
        guestTeamId = in.readLong();
        hostTeamName = in.readString();
        hostTeamLogo = in.readString();
        guestTeamLogo = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        cost = in.readDouble();
        guestTeamName = in.readString();
    }

    public static final Creator<ResultAboutBallDetailsBean> CREATOR = new Creator<ResultAboutBallDetailsBean>() {
        @Override
        public ResultAboutBallDetailsBean createFromParcel(Parcel in) {
            return new ResultAboutBallDetailsBean(in);
        }

        @Override
        public ResultAboutBallDetailsBean[] newArray(int size) {
            return new ResultAboutBallDetailsBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(offerId);
        dest.writeLong(stadiumId);
        dest.writeLong(areaId);
        dest.writeLong(calendarId);
        dest.writeString(stadiumName);
        dest.writeLong(hostTeamId);
        dest.writeLong(guestTeamId);
        dest.writeString(hostTeamName);
        dest.writeString(hostTeamLogo);
        dest.writeString(guestTeamLogo);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeDouble(cost);
        dest.writeString(guestTeamName);
    }
}
