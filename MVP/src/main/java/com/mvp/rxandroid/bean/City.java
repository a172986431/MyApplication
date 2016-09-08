package com.mvp.rxandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class City implements Parcelable{
    public String province_cn;
    public String district_cn;
    public String name_cn;
    public String name_en;
    public String area_id;

    protected City(Parcel in) {
        province_cn = in.readString();
        district_cn = in.readString();
        name_cn = in.readString();
        name_en = in.readString();
        area_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(province_cn);
        dest.writeString(district_cn);
        dest.writeString(name_cn);
        dest.writeString(name_en);
        dest.writeString(area_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
