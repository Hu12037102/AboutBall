package com.work.guaishouxingqiu.aboutball.venue.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/9 14:56
 * 更新时间: 2019/8/9 14:56
 * 描述:
 */
public class ResultNotBookBean implements Parcelable {
    public long agreeId;
    public long calendarId;
    public String stadiumName;
    public long hostTeamId;
    public long guestTeamId;
    public String hostTeamName;
    public String hostShirtColor;
    public String guestShirtColor;
    public String hostTeamLogo;
    public String guestTeamLogo;
    public String guestTeamName;
    public String startTime;
    public String endTime;
    public String cost;
    public int state;
    public String phone;
    public int myAgreeBall;
    public boolean isCheck;


    protected ResultNotBookBean(Parcel in) {
        agreeId = in.readLong();
        calendarId = in.readLong();
        stadiumName = in.readString();
        hostTeamId = in.readLong();
        guestTeamId = in.readLong();
        hostTeamName = in.readString();
        hostShirtColor = in.readString();
        guestShirtColor = in.readString();
        hostTeamLogo = in.readString();
        guestTeamLogo = in.readString();
        guestTeamName = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        cost = in.readString();
        state = in.readInt();
        phone = in.readString();
        myAgreeBall = in.readInt();
        isCheck = in.readByte() != 0;
    }

    public static final Creator<ResultNotBookBean> CREATOR = new Creator<ResultNotBookBean>() {
        @Override
        public ResultNotBookBean createFromParcel(Parcel in) {
            return new ResultNotBookBean(in);
        }

        @Override
        public ResultNotBookBean[] newArray(int size) {
            return new ResultNotBookBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(agreeId);
        dest.writeLong(calendarId);
        dest.writeString(stadiumName);
        dest.writeLong(hostTeamId);
        dest.writeLong(guestTeamId);
        dest.writeString(hostTeamName);
        dest.writeString(hostShirtColor);
        dest.writeString(guestShirtColor);
        dest.writeString(hostTeamLogo);
        dest.writeString(guestTeamLogo);
        dest.writeString(guestTeamName);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(cost);
        dest.writeInt(state);
        dest.writeString(phone);
        dest.writeInt(myAgreeBall);
        dest.writeByte((byte) (isCheck ? 1 : 0));
    }
}
