package com.ifeell.app.aboutball.game.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 13:21
 * 更新时间: 2019/3/25 13:21
 * 描述:
 */
public class ResultGameSimpleBean implements Parcelable {
    public String endTime;
    public String gameName;
    public String guestLogoUrl;
    public String guestName;
    public String guestScore;
    public String hostLogoUrl;
    public String hostName;
    public String hostScore;
    public String liveAddress;
    public String livePhoto;
    public String liveType;
    public int matchId;
    public String matchName;
    public String matchState;
    public String startTime;
    public int stateId;
    public String typeId;


    protected ResultGameSimpleBean(Parcel in) {
        endTime = in.readString();
        gameName = in.readString();
        guestLogoUrl = in.readString();
        guestName = in.readString();
        guestScore = in.readString();
        hostLogoUrl = in.readString();
        hostName = in.readString();
        hostScore = in.readString();
        liveAddress = in.readString();
        livePhoto = in.readString();
        liveType = in.readString();
        matchId = in.readInt();
        matchName = in.readString();
        matchState = in.readString();
        startTime = in.readString();
        stateId = in.readInt();
        typeId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(endTime);
        dest.writeString(gameName);
        dest.writeString(guestLogoUrl);
        dest.writeString(guestName);
        dest.writeString(guestScore);
        dest.writeString(hostLogoUrl);
        dest.writeString(hostName);
        dest.writeString(hostScore);
        dest.writeString(liveAddress);
        dest.writeString(livePhoto);
        dest.writeString(liveType);
        dest.writeInt(matchId);
        dest.writeString(matchName);
        dest.writeString(matchState);
        dest.writeString(startTime);
        dest.writeInt(stateId);
        dest.writeString(typeId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultGameSimpleBean> CREATOR = new Creator<ResultGameSimpleBean>() {
        @Override
        public ResultGameSimpleBean createFromParcel(Parcel in) {
            return new ResultGameSimpleBean(in);
        }

        @Override
        public ResultGameSimpleBean[] newArray(int size) {
            return new ResultGameSimpleBean[size];
        }
    };
}
