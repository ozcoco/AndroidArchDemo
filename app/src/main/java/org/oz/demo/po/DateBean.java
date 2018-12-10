package org.oz.demo.po;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DateBean implements Parcelable, Serializable
{

    private String date;


    public DateBean()
    {
    }

    public DateBean(String date)
    {
        this.date = date;
    }

    protected DateBean(Parcel in)
    {
        date = in.readString();
    }

    public static final Creator<DateBean> CREATOR = new Creator<DateBean>()
    {
        @Override
        public DateBean createFromParcel(Parcel in)
        {
            return new DateBean(in);
        }

        @Override
        public DateBean[] newArray(int size)
        {
            return new DateBean[size];
        }
    };

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(date);
    }
}
