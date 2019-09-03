package com.work.guaishouxingqiu.aboutball.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/3 15:58
 * 更新时间: 2019/9/3 15:58
 * 描述:门票列表bean
 */
public class ResultTicketMallBean implements Parcelable {
    public int contentType; //"内容类型: 1.比赛门票，2.泳票",
    public String guestLogoUrl;
    public String guestTeamName;
    public String hostLogoUrl;
    public String hostTeamName;
    public String image;
    public long matchId;
    public int saleStatus;//"商品状态：1购票,2已售完,3未开售",
    public long skuId;
    public String startTime;
    public String subTitle;
    public String title;

    protected ResultTicketMallBean(Parcel in) {
        contentType = in.readInt();
        guestLogoUrl = in.readString();
        guestTeamName = in.readString();
        hostLogoUrl = in.readString();
        hostTeamName = in.readString();
        image = in.readString();
        matchId = in.readLong();
        saleStatus = in.readInt();
        skuId = in.readLong();
        startTime = in.readString();
        subTitle = in.readString();
        title = in.readString();
    }

    public static final Creator<ResultTicketMallBean> CREATOR = new Creator<ResultTicketMallBean>() {
        @Override
        public ResultTicketMallBean createFromParcel(Parcel in) {
            return new ResultTicketMallBean(in);
        }

        @Override
        public ResultTicketMallBean[] newArray(int size) {
            return new ResultTicketMallBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contentType);
        dest.writeString(guestLogoUrl);
        dest.writeString(guestTeamName);
        dest.writeString(hostLogoUrl);
        dest.writeString(hostTeamName);
        dest.writeString(image);
        dest.writeLong(matchId);
        dest.writeInt(saleStatus);
        dest.writeLong(skuId);
        dest.writeString(startTime);
        dest.writeString(subTitle);
        dest.writeString(title);
    }
}
