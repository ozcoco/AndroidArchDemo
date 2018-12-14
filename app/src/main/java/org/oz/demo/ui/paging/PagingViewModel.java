package org.oz.demo.ui.paging;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.oz.demo.po.User;
import org.oz.demo.utils.ToastUtils;
import org.oz.demo.webservice.WebService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.schedulers.Schedulers;

public class PagingViewModel extends AndroidViewModel {

    public final MutableLiveData<List<User>> users;

    public PagingViewModel(@NonNull Application application) {
        super(application);

        users = new MutableLiveData<>();

        users.setValue(new ArrayList<>());

        getUsers();

    }


    @Override
    protected void onCleared() {
        super.onCleared();

    }

    private final class LoadDataWorker extends Worker {


        public LoadDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);



        }

        @SuppressLint("CheckResult")
        @NonNull
        @Override
        public Result doWork() {

            ToastUtils.info(getApplication(), "doWork ..............", Gravity.CENTER, Toast.LENGTH_SHORT);

            WebService.getInstance().getUserService().users(1).observeOn(Schedulers.io()).subscribeOn(Schedulers.computation()).subscribe(data ->
                    users.postValue(data.getUsers()), throwable ->
            {


            });



            return Result.success();
        }
    }


    @SuppressLint("CheckResult")
    public void getUsers() {

        Constraints myConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest loadDataWork =
                new OneTimeWorkRequest.Builder(LoadDataWorker.class).addTag("loadDataWork")
//                        .setConstraints(myConstraints)
                        .build();

        WorkManager.getInstance().beginUniqueWork("loadDataWork", ExistingWorkPolicy.APPEND, loadDataWork).enqueue().getState().observeForever(state -> {

            ToastUtils.info(getApplication(), state.toString(), Gravity.CENTER, Toast.LENGTH_SHORT);

        });

    }


}
