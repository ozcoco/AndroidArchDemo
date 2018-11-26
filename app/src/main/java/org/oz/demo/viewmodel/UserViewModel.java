package org.oz.demo.viewmodel;

import android.text.TextUtils;

import org.oz.demo.BR;
import org.oz.demo.base.BaseViewModel;
import org.oz.demo.po.User;

import androidx.databinding.Bindable;

public class UserViewModel extends BaseViewModel
{
    private User user;

    @Bindable
    public String getName()
    {
        return user.getName();
    }

    public void setName(String name)
    {
        if (!TextUtils.equals(user.getName(), name))
            this.user.setName(name);

        notifyPropertyChanged(BR.name);

    }

    @Bindable
    public int getAge()
    {
        return user.getAge();
    }

    public void setAge(int age)
    {
        if (user.getAge() != age)
            this.user.setAge(age);
        notifyPropertyChanged(BR.age);
    }


    public UserViewModel()
    {
        user = new User();
    }

    public UserViewModel(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
