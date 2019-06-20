package com.work.guaishouxingqiu.aboutball.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 15:07
 * 更新时间: 2019/3/19 15:07
 * 描述:
 */
public class ResultCommunityDataBean implements Parcelable{
    public long tweetId;
    public String tweetContent;
    public String imageUrl;
    public long userId;
    public String releaseTime;
    public String nickName;
    public String topicTitle;
    public int praiseCount;
    public int commentCount;
    public int shareCount;
    public String headImg;
    public int hasPraise; //自己有沒有点赞
    public int myTweet; //自己的话题
    public int hasFollow;//有没有关注
    public ResultRecommendHotBean topic;


    protected ResultCommunityDataBean(Parcel in) {
        tweetId = in.readLong();
        tweetContent = in.readString();
        imageUrl = in.readString();
        userId = in.readLong();
        releaseTime = in.readString();
        nickName = in.readString();
        topicTitle = in.readString();
        praiseCount = in.readInt();
        commentCount = in.readInt();
        shareCount = in.readInt();
        headImg = in.readString();
        hasPraise = in.readInt();
        myTweet = in.readInt();
        hasFollow = in.readInt();
        topic = in.readParcelable(ResultRecommendHotBean.class.getClassLoader());
    }

    public static final Creator<ResultCommunityDataBean> CREATOR = new Creator<ResultCommunityDataBean>() {
        @Override
        public ResultCommunityDataBean createFromParcel(Parcel in) {
            return new ResultCommunityDataBean(in);
        }

        @Override
        public ResultCommunityDataBean[] newArray(int size) {
            return new ResultCommunityDataBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(tweetId);
        dest.writeString(tweetContent);
        dest.writeString(imageUrl);
        dest.writeLong(userId);
        dest.writeString(releaseTime);
        dest.writeString(nickName);
        dest.writeString(topicTitle);
        dest.writeInt(praiseCount);
        dest.writeInt(commentCount);
        dest.writeInt(shareCount);
        dest.writeString(headImg);
        dest.writeInt(hasPraise);
        dest.writeInt(myTweet);
        dest.writeInt(hasFollow);
        dest.writeParcelable(topic, flags);
    }
}
