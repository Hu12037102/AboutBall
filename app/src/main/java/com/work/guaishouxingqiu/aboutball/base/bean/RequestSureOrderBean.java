package com.work.guaishouxingqiu.aboutball.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/10 11:40
 * 更新时间: 2019/9/10 11:40
 * 描述:
 */
public class RequestSureOrderBean implements Parcelable {
    public RequestSureOrderBean(){}
    public RequestSureOrderBean(long spuId){
        this.spuId = spuId;
    }
    public long spuId;
    public String params;
    public int num = 1;

    protected RequestSureOrderBean(Parcel in) {
        spuId = in.readLong();
        params = in.readString();
        num = in.readInt();
    }

    public static final Creator<RequestSureOrderBean> CREATOR = new Creator<RequestSureOrderBean>() {
        @Override
        public RequestSureOrderBean createFromParcel(Parcel in) {
            return new RequestSureOrderBean(in);
        }

        @Override
        public RequestSureOrderBean[] newArray(int size) {
            return new RequestSureOrderBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(spuId);
        dest.writeString(params);
        dest.writeInt(num);
    }
}
