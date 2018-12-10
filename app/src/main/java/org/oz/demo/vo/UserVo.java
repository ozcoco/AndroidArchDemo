package org.oz.demo.vo;

import android.os.Parcelable;

import androidx.databinding.Observable;

import org.oz.demo.po.Message;
import org.oz.demo.po.User;

import java.io.Serializable;
import java.util.List;

public class UserVo extends Message implements Parcelable, Serializable
{

    private User user;

    private List<User> users;


    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers(List<User> users)
    {
        this.users = users;
    }

    public static UserVo newInstance()
    {

        return new UserVo();
    }


}
