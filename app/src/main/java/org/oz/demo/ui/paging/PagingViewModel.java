package org.oz.demo.ui.paging;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.oz.demo.po.User;
import org.oz.demo.webservice.WebService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class PagingViewModel extends AndroidViewModel
{

    public final MutableLiveData<List<User>> users;

    public PagingViewModel(@NonNull Application application)
    {
        super(application);

        users = new MutableLiveData<>();

        users.setValue(new ArrayList<>());

        getUsers();

    }


    @Override
    protected void onCleared()
    {
        super.onCleared();

    }

    @SuppressLint("CheckResult")
    public void getUsers()
    {

        WebService.getInstance().getUserService().users(1).observeOn(Schedulers.io()).subscribeOn(Schedulers.computation()).subscribe(data ->
        {
            users.postValue(data.getUsers());

        }, throwable ->
        {


        });

    }


}
