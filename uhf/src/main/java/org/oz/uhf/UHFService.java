package org.oz.uhf;

import android.content.Context;

import androidx.annotation.Nullable;

public class UHFService implements IUHFService {

    private IUHFService uhfService;

    private UHFService() {

        uhfService = UHFServiceImpl.newInstance();

    }

    private static class Singleton {
        static UHFService INSTANCE = new UHFService();
    }

    public static IUHFService getInstance() {
        return Singleton.INSTANCE;
    }


    @Override
    public void init(@Nullable Context context) {

        uhfService.init(context);

    }

    @Override
    public void onCleared() {

        if (uhfService != null)
            uhfService.onCleared();

        uhfService = null;
    }
}
