package com.ifeell.app.aboutball.venue.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/5 15:05
 * 更新时间: 2019/5/5 15:05
 * 描述:我的球队bean
 */
public class ResultMyBallTeamBean implements Parcelable {
    public long teamId;
    public String teamLogo;
    public String teamName;
    public String leaderName;
    public int isLeader;
    public boolean isCheck;

    protected ResultMyBallTeamBean(Parcel in) {
        teamId = in.readLong();
        teamLogo = in.readString();
        teamName = in.readString();
        leaderName = in.readString();
        isLeader = in.readInt();
        isCheck = in.readByte() != 0;
    }

    public static final Creator<ResultMyBallTeamBean> CREATOR = new Creator<ResultMyBallTeamBean>() {
        @Override
        public ResultMyBallTeamBean createFromParcel(Parcel in) {
            return new ResultMyBallTeamBean(in);
        }

        @Override
        public ResultMyBallTeamBean[] newArray(int size) {
            return new ResultMyBallTeamBean[size];
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
        dest.writeByte((byte) (isCheck ? 1 : 0));
    }
}
