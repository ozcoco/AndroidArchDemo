package org.oz.demo.po;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.oz.demo.BR;

public class DateViewModel extends BaseObservable
{

    private DateBean date;

    @Bindable
    public String getTime()
    {
        return date.getDate();
    }

    public void setTime(String time)
    {
        if (TextUtils.equals(date.getDate(), time))
            return;

        date.setDate(time);

        notifyPropertyChanged(BR.time);

    }


    public DateViewModel()
    {

        date = new DateBean();

    }

    public DateViewModel(DateBean date)
    {
        this.date = date;

        notifyChange();

    }

    public DateBean getDate()
    {
        return date;
    }

    public void setDate(DateBean date)
    {
        this.date = date;

        notifyChange();

    }
}
