package org.oz.demo.vo;

import android.os.Parcel;
import android.os.Parcelable;

import org.oz.demo.base.BaseBean;
import org.oz.demo.po.User;

import java.io.Serializable;
import java.util.List;

public class UserVo extends BaseBean implements Parcelable, Serializable {

    private User user;

    private List<User> users;


    protected UserVo(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        users = in.createTypedArrayList(User.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeTypedList(users);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserVo> CREATOR = new Creator<UserVo>() {
        @Override
        public UserVo createFromParcel(Parcel in) {
            return new UserVo(in);
        }

        @Override
        public UserVo[] newArray(int size) {
            return new UserVo[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
