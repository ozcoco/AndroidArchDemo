package org.oz.demo.ui.paging;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.RxWorker;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.oz.demo.po.User;
import org.oz.demo.service.ServiceManager;
import org.oz.demo.utils.GsonUtils;
import org.oz.demo.vo.UserVo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
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


    public void getUsers() {

        Constraints myConstraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

        OneTimeWorkRequest loadDataWork = new OneTimeWorkRequest.Builder(LoadDataWorker2.class).setInputData(new Data.Builder().putInt("page", 2).build()).build();

        WorkManager.getInstance().enqueue(loadDataWork);

        WorkManager.getInstance().getWorkInfoByIdLiveData(loadDataWork.getId()).observeForever(info -> {

            if (info != null && info.getState().isFinished() && info.getState() == WorkInfo.State.SUCCEEDED) {

                final String json = info.getOutputData().getString("data");

                final UserVo vo = GsonUtils.convertString2Object(json, UserVo.class);

                users.setValue(vo.getUsers());
            }

        });

    }

}


final class LoadDataWorker extends Worker {

    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */

    private final int page;

    public LoadDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        page = workerParams.getInputData().getInt("page", 1);
    }


    @NonNull
    @Override
    public Result doWork() {
        try {

            Log.i(getClass().getCanonicalName(), "LoadDataWorker start .............");

            final UserVo data = ServiceManager.getInstance().getWebservice().getUserService().users2(page).execute().body();

            final Data.Builder builder = new Data.Builder();

            final String json = GsonUtils.convertVO2String(data);

            Log.i(getClass().getCanonicalName(), json);

            builder.putString("data", json);

            return Result.success(builder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return Result.failure();
    }

}

final class LoadDataWorker2 extends RxWorker {

    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */

    private final int page;

    public LoadDataWorker2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        page = workerParams.getInputData().getInt("page", 1);
    }

    @Override
    public Single<Result> createWork() {

        return new Single<Result>() {
            @Override
            protected void subscribeActual(SingleObserver<? super Result> observer) {

                ServiceManager.getInstance().getWebservice().getUserService().users(page).observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribe(new Observer<UserVo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        observer.onSubscribe(d);

                    }

                    @Override
                    public void onNext(UserVo data) {

                        final Data.Builder builder = new Data.Builder();

                        final String json = GsonUtils.convertVO2String(data);

                        builder.putString("data", json);

                        observer.onSuccess(Result.success(builder.build()));

                    }

                    @Override
                    public void onError(Throwable e) {

                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {

                        Log.i(getClass().getCanonicalName(), "onComplete...............");
                    }
                });

            }
        };

    }
}