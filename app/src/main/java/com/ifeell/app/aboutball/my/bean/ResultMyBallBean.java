package com.ifeell.app.aboutball.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 10:56
 * 更新时间: 2019/4/24 10:56
 * 描述:我的球队bean
 */
public class ResultMyBallBean implements Parcelable {
    public long teamId;
    public String teamLogo;
    public String teamName;
    public String leaderName;
    public int isLeader;//1：队长；0：不是队长

    protected ResultMyBallBean(Parcel in) {
        teamId = in.readLong();
        teamLogo = in.readString();
        teamName = in.readString();
        leaderName = in.readString();
        isLeader = in.readInt();
    }

    public static final Creator<ResultMyBallBean> CREATOR = new Creator<ResultMyBallBean>() {
        @Override
        public ResultMyBallBean createFromParcel(Parcel in) {
            return new ResultMyBallBean(in);
        }

        @Override
        public ResultMyBallBean[] newArray(int size) {
            return new ResultMyBallBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(teamId);
        dest.writeString(teamLogo);
        dest.writeString(teamName);
        dest.writeString(leaderName);
        dest.writeInt(isLeader);
    }
}
