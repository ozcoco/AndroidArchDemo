package org.oz.demo.po;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import org.oz.demo.BR;

public class User extends Message implements Parcelable, Observable
{

    private int userId;

    private String name;

    private int age;

    private int gender;

    private String address;

    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    public User()
    {
    }

    protected User(Parcel in)
    {
        userId = in.readInt();
        name = in.readString();
        age = in.readInt();
        gender = in.readInt();
        address = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel in)
        {
            return new User(in);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeInt(gender);
        dest.writeString(address);
    }

    @Bindable
    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
        notifyChange(BR.userId);
    }

    @Bindable
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        notifyChange(BR.name);
    }

    @Bindable
    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
        notifyChange(BR.age);
    }

    @Bindable
    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
        notifyChange(BR.gender);
    }

    @Bindable
    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
        notifyChange(BR.address);
    }

    private synchronized void notifyChange(int propertyId)
    {
        if (propertyChangeRegistry == null)
        {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange(this, propertyId);
    }

    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback)
    {
        if (propertyChangeRegistry == null)
        {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add(callback);

    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback)
    {
        if (propertyChangeRegistry != null)
        {
            propertyChangeRegistry.remove(callback);
        }
    }
}
