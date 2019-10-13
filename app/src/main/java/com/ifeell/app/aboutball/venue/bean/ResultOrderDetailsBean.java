package com.ifeell.app.aboutball.venue.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 16:22
 * 更新时间: 2019/4/22 16:22
 * 描述:
 */
public class ResultOrderDetailsBean implements Parcelable {
    public long orderId;
    public String stadiumName;
    public String orderTime;
    public int stateId;
    public String stateName;
    public String address;
    public String orderNo;
    public String phoneNum;
    public String createOrderTime;
    public String payTime;
    public float totalPrice;
    public float realPrice;
    public List<OrderPeopleCountBean> orderDetailForOrders;
    public float grade;
    public double longitude;
    public double latitude;
    public int orderType;

    protected ResultOrderDetailsBean(Parcel in) {
        orderId = in.readLong();
        stadiumName = in.readString();
        orderTime = in.readString();
        stateId = in.readInt();
        stateName = in.readString();
        address = in.readString();
        orderNo = in.readString();
        phoneNum = in.readString();
        createOrderTime = in.readString();
        payTime = in.readString();
        totalPrice = in.readFloat();
        realPrice = in.readFloat();
        grade = in.readFloat();
        longitude = in.readDouble();
        latitude = in.readDouble();
        orderDetailForOrders = in.createTypedArrayList(OrderPeopleCountBean.CREATOR);
        orderType = in.readInt();
    }

    public static final Creator<ResultOrderDetailsBean> CREATOR = new Creator<ResultOrderDetailsBean>() {
        @Override
        public ResultOrderDetailsBean createFromParcel(Parcel in) {
            return new ResultOrderDetailsBean(in);
        }

        @Override
        public ResultOrderDetailsBean[] newArray(int size) {
            return new ResultOrderDetailsBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(orderId);
        dest.writeString(stadiumName);
        dest.writeString(orderTime);
        dest.writeInt(stateId);
        dest.writeString(stateName);
        dest.writeString(address);
        dest.writeString(orderNo);
        dest.writeString(phoneNum);
        dest.writeString(createOrderTime);
        dest.writeString(payTime);
        dest.writeFloat(totalPrice);
        dest.writeFloat(realPrice);
        dest.writeFloat(grade);
        dest.writeTypedList(orderDetailForOrders);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeInt(orderType);
    }

    public static class OrderPeopleCountBean implements Parcelable {
        public String areaName;
        public String calendar;
        public float price;

        protected OrderPeopleCountBean(Parcel in) {
            areaName = in.readString();
            calendar = in.readString();
            price = in.readFloat();
        }

        public static final Creator<OrderPeopleCountBean> CREATOR = new Creator<OrderPeopleCountBean>() {
            @Override
            public OrderPeopleCountBean createFromParcel(Parcel in) {
                return new OrderPeopleCountBean(in);
            }

            @Override
            public OrderPeopleCountBean[] newArray(int size) {
                return new OrderPeopleCountBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(areaName);
            dest.writeString(calendar);
            dest.writeFloat(price);
        }
    }

}
