package org.oz.demo.ui.paging;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.autofill.AutofillId;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.RxWorker;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

import org.oz.demo.po.User;
import org.oz.demo.service.ServiceManager;
import org.oz.demo.utils.GsonUtils;
import org.oz.demo.utils.ToastUtils;
import org.oz.demo.vo.UserVo;
import org.oz.demo.webservice.WebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.observers.BlockingBaseObserver;
import io.reactivex.schedulers.Schedulers;

public class PagingViewModel extends AndroidViewModel
{

    public MutableLiveData<UserVo> vo;

    public final LiveData<List<User>> users;

    public PagingViewModel(@NonNull Application application)
    {
        super(application);

        vo = new MutableLiveData<>();

        users = Transformations.map(vo, UserVo::getUsers);

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
        Constraints myConstraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

        OneTimeWorkRequest loadDataWork = new OneTimeWorkRequest.Builder(LoadDataWorker.class).setInputData(new Data.Builder().putInt("page", 2).build()).setConstraints(myConstraints).build();

        WorkManager.getInstance().enqueue(loadDataWork);

        WorkManager.getInstance().getWorkInfoByIdLiveData(loadDataWork.getId()).observeForever(info ->
        {

            if (info != null && info.getState().isFinished() && info.getState() == WorkInfo.State.SUCCEEDED)
            {
                final String json = info.getOutputData().getString("data");

                final UserVo data = GsonUtils.convertString2Object(json, UserVo.class);

                vo.setValue(data);
            }

        });


    }


}


final class LoadDataWorker extends RxWorker
{

    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */

    private final int page;

    public LoadDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);

        page = workerParams.getInputData().getInt("page", 1);
    }



    @NonNull
    @Override
    public Single<Result> createWork()
    {

        return new Single<Result>()
        {
            @Override
            protected void subscribeActual(SingleObserver<? super Result> observer)
            {

                final Observable<UserVo> users = ServiceManager.getInstance().getWebservice().getUserService().users(page);

                users.observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribe(new Observer<UserVo>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                        observer.onSubscribe(d);
                    }

                    @Override
                    public void onNext(UserVo data)
                    {
                        final Data.Builder builder = new Data.Builder();

                        builder.putString("data", GsonUtils.convertVO2String(data));

                        observer.onSuccess(Result.success(builder.build()));

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                        observer.onError(e);

                    }

                    @Override
                    public void onComplete()
                    {
                        Log.i(getClass().getCanonicalName(), "LoadDataWorker end .............");
                    }
                });


            }
        };
    }
}