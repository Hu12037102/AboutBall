package com.work.guaishouxingqiu.aboutball.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/26 11:16
 * 更新时间: 2019/4/26 11:16
 * 描述:球队成员信息
 */
public class ResultTeamDetailsMemberBean implements Parcelable{
    public long playerId;
    public String nickName;
    public String imageUrl;
    public int isLeader;
    public String position;
    public int number = -1;
    public int isMe;//1、是自己 0、代表不是
    public String secondPosition;

    protected ResultTeamDetailsMemberBean(Parcel in) {
        playerId = in.readLong();
        nickName = in.readString();
        imageUrl = in.readString();
        isLeader = in.readInt();
        position = in.readString();
        number = in.readInt();
        isMe = in.readInt();
        secondPosition =in.readString();
    }

    public static final Creator<ResultTeamDetailsMemberBean> CREATOR = new Creator<ResultTeamDetailsMemberBean>() {
        @Override
        public ResultTeamDetailsMemberBean createFromParcel(Parcel in) {
            return new ResultTeamDetailsMemberBean(in);
        }

        @Override
        public ResultTeamDetailsMemberBean[] newArray(int size) {
            return new ResultTeamDetailsMemberBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(playerId);
        dest.writeString(nickName);
        dest.writeString(imageUrl);
        dest.writeInt(isLeader);
        dest.writeString(position);
        dest.writeInt(number);
        dest.writeInt(isMe);
        dest.writeString(secondPosition);
    }
}
