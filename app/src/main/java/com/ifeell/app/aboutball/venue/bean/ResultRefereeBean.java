package com.ifeell.app.aboutball.venue.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/23 15:46
 * 更新时间: 2019/4/23 15:46
 * 描述:裁判bean
 */
public class ResultRefereeBean implements Parcelable{
    public long refereeId;
    public String photo;
    public String name;
    public int matchCount;
    public String commentCount;
    public String level;
    public String invitation;
    public boolean isInvite;//是否被邀请

    protected ResultRefereeBean(Parcel in) {
        refereeId = in.readLong();
        photo = in.readString();
        name = in.readString();
        matchCount = in.readInt();
        commentCount = in.readString();
        level = in.readString();
        invitation = in.readString();
        isInvite = in.readByte() != 0;
    }

    public static final Creator<ResultRefereeBean> CREATOR = new Creator<ResultRefereeBean>() {
        @Override
        public ResultRefereeBean createFromParcel(Parcel in) {
            return new ResultRefereeBean(in);
        }

        @Override
        public ResultRefereeBean[] newArray(int size) {
            return new ResultRefereeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(refereeId);
        dest.writeString(photo);
        dest.writeString(name);
        dest.writeInt(matchCount);
        dest.writeString(commentCount);
        dest.writeString(level);
        dest.writeString(invitation);
        dest.writeByte((byte) (isInvite ? 1 : 0));
    }
}
