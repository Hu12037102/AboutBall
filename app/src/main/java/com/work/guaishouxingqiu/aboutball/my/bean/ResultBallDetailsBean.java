package com.work.guaishouxingqiu.aboutball.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 17:42
 * 更新时间: 2019/4/25 17:42
 * 描述:球队详情bean
 */
public class ResultBallDetailsBean implements Parcelable {
    public long teamId;
    public String teamLogo;
    public String teamName;
    public String teamType;
    public String playerCount;
    public String shirtColor;
    public int teamTypeId;
    public List<MatchBean> matchForRecnetList;



    public static class MatchBean implements Parcelable {
        public long matchId;
        public String startTime;
        public String endTime;
        public String hostTeamLogo;
        public String hostTeamName;
        public int hostScore;
        public String guestTeamLogo;
        public String guestTeamName;
        public int guestScore;

        protected MatchBean(Parcel in) {
            matchId = in.readLong();
            startTime = in.readString();
            endTime = in.readString();
            hostTeamLogo = in.readString();
            hostTeamName = in.readString();
            hostScore = in.readInt();
            guestTeamLogo = in.readString();
            guestTeamName = in.readString();
            guestScore = in.readInt();
        }

        public static final Creator<MatchBean> CREATOR = new Creator<MatchBean>() {
            @Override
            public MatchBean createFromParcel(Parcel in) {
                return new MatchBean(in);
            }

            @Override
            public MatchBean[] newArray(int size) {
                return new MatchBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(matchId);
            dest.writeString(startTime);
            dest.writeString(endTime);
            dest.writeString(hostTeamLogo);
            dest.writeString(hostTeamName);
            dest.writeInt(hostScore);
            dest.writeString(guestTeamLogo);
            dest.writeString(guestTeamName);
            dest.writeInt(guestScore);
        }
    }

    protected ResultBallDetailsBean(Parcel in) {
        teamId = in.readLong();
        teamLogo = in.readString();
        teamName = in.readString();
        teamType = in.readString();
        playerCount = in.readString();
        shirtColor = in.readString();
        teamTypeId = in.readInt();
        matchForRecnetList = in.createTypedArrayList(MatchBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(teamId);
        dest.writeString(teamLogo);
        dest.writeString(teamName);
        dest.writeString(teamType);
        dest.writeString(playerCount);
        dest.writeString(shirtColor);
        dest.writeInt(teamTypeId);
        dest.writeTypedList(matchForRecnetList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultBallDetailsBean> CREATOR = new Creator<ResultBallDetailsBean>() {
        @Override
        public ResultBallDetailsBean createFromParcel(Parcel in) {
            return new ResultBallDetailsBean(in);
        }

        @Override
        public ResultBallDetailsBean[] newArray(int size) {
            return new ResultBallDetailsBean[size];
        }
    };
}
