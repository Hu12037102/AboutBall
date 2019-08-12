package com.work.guaishouxingqiu.aboutball.venue.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/16 17:39
 * 更新时间: 2019/5/16 17:39
 * 描述:约球详情bean
 */
public class ResultAboutBallDetailsBean implements Parcelable {
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
    public long agreeId;
    public String hostShirtColor;
    public String guestShirtColor;
    public int myAgreeBall;
    public String phone;


    protected ResultAboutBallDetailsBean(Parcel in) {
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
        agreeId = in.readLong();
        hostShirtColor = in.readString();
        guestShirtColor = in.readString();
        myAgreeBall = in.readInt();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
        dest.writeLong(agreeId);
        dest.writeString(hostShirtColor);
        dest.writeString(guestShirtColor);
        dest.writeInt(myAgreeBall);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
