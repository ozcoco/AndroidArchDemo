package org.oz.demo.ui.mainactivity2;

import android.annotation.SuppressLint;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.oz.demo.po.DateViewModel;
import org.oz.demo.po.User;
import org.oz.demo.webservice.WebService;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MainActivity2ViewModel extends ViewModel {

    public final ObservableField<String> msg = new ObservableField<>();

    public final ObservableField<String> date = new ObservableField<>();

    public final DateViewModel dateViewModel = new DateViewModel();

    public final MutableLiveData<List<User>> users = new MutableLiveData<>();

    public final User user = new User();

    public MutableLiveData<User> userData;

    public MutableLiveData<User> getUserData() {

        if (userData == null) {
            userData = new MutableLiveData<>();
            getUser();
        }

        return userData;
    }

    private final WebService webService;

    {
        webService = WebService.getInstance();
    }


    @SuppressLint("CheckResult")
    public void getUser() {

        webService.getUserService().user(2436).observeOn(Schedulers.io()).subscribeOn(Schedulers.computation()).subscribe(data ->
        {

            userData.postValue(data);

            user.setName(data.getName());

        }, throwable ->
        {


        });

        webService.getUserService().users2(1).observeOn(Schedulers.io()).subscribeOn(Schedulers.computation()).subscribe(msg ->
        {
            users.postValue(msg.getData());

        }, throwable ->
        {


        });

    }


}
