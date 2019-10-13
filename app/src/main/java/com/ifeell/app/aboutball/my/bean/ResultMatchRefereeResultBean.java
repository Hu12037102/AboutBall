package com.ifeell.app.aboutball.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/10 10:26
 * 更新时间: 2019/6/10 10:26
 * 描述:赛况记录列表bean
 */
public class ResultMatchRefereeResultBean implements Parcelable {
    public String teamAndAction;
    public List<ChildBean> agreeOutsForSimpleList;

    protected ResultMatchRefereeResultBean(Parcel in) {
        teamAndAction = in.readString();
        agreeOutsForSimpleList = in.createTypedArrayList(ChildBean.CREATOR);
    }

    public static final Creator<ResultMatchRefereeResultBean> CREATOR = new Creator<ResultMatchRefereeResultBean>() {
        @Override
        public ResultMatchRefereeResultBean createFromParcel(Parcel in) {
            return new ResultMatchRefereeResultBean(in);
        }

        @Override
        public ResultMatchRefereeResultBean[] newArray(int size) {
            return new ResultMatchRefereeResultBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teamAndAction);
        dest.writeTypedList(agreeOutsForSimpleList);
    }

    public static class ChildBean implements Parcelable {
        public String headerImg;
        public String playerName;
        public int number;
        public int duration;
        public long outsId;
        public long teamId;
        public String teamName;
        public long playerId;
        public String action;

        protected ChildBean(Parcel in) {
            headerImg = in.readString();
            playerName = in.readString();
            number = in.readInt();
            duration = in.readInt();
            outsId = in.readLong();
            teamId = in.readLong();
            teamName = in.readString();
            playerId = in.readLong();
            action = in.readString();
        }

        public static final Creator<ChildBean> CREATOR = new Creator<ChildBean>() {
            @Override
            public ChildBean createFromParcel(Parcel in) {
                return new ChildBean(in);
            }

            @Override
            public ChildBean[] newArray(int size) {
                return new ChildBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(headerImg);
            dest.writeString(playerName);
            dest.writeInt(number);
            dest.writeInt(duration);
            dest.writeLong(outsId);
            dest.writeLong(teamId);
            dest.writeString(teamName);
            dest.writeLong(playerId);
            dest.writeString(action);
        }
    }
}
