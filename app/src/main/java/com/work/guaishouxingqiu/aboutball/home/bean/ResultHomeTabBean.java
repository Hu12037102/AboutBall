package com.work.guaishouxingqiu.aboutball.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/14 17:29
 * 更新时间: 2019/3/14 17:29
 * 描述: 首页tab
 */
public class ResultHomeTabBean implements Parcelable {
    public ResultHomeTabBean(String parentLabelName) {
        this.parentLabelName = parentLabelName;
    }

    public int parentLabelId;
    public String parentLabelName;
    public List<ResultHomeTabBean.LabBean> homeLabelList;

    protected ResultHomeTabBean(Parcel in) {
        parentLabelId = in.readInt();
        parentLabelName = in.readString();
        homeLabelList = in.createTypedArrayList(LabBean.CREATOR);
    }

    public static final Creator<ResultHomeTabBean> CREATOR = new Creator<ResultHomeTabBean>() {
        @Override
        public ResultHomeTabBean createFromParcel(Parcel in) {
            return new ResultHomeTabBean(in);
        }

        @Override
        public ResultHomeTabBean[] newArray(int size) {
            return new ResultHomeTabBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(parentLabelId);
        dest.writeString(parentLabelName);
        dest.writeTypedList(homeLabelList);
    }

    public static class LabBean implements Parcelable{
        public int labelId;
        public String labelName;
        public boolean isCheck;


        protected LabBean(Parcel in) {
            labelId = in.readInt();
            labelName = in.readString();
            isCheck = in.readByte() != 0;
        }

        public static final Creator<LabBean> CREATOR = new Creator<LabBean>() {
            @Override
            public LabBean createFromParcel(Parcel in) {
                return new LabBean(in);
            }

            @Override
            public LabBean[] newArray(int size) {
                return new LabBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(labelId);
            dest.writeString(labelName);
            dest.writeByte((byte) (isCheck ? 1 : 0));
        }
    }
}
