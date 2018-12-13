package org.oz.demo;

import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;

import org.oz.demo.service.ServiceManager;

/**
 * Created by oz on 07/03/18.
 */

public class MainApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        loadComponent();
    }


    private void loadComponent() {

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Utils.init(getApplicationContext());

        Stetho.initializeWithDefaults(this);

        ServiceManager.init(this);

    }


    private void destroy() {

        ServiceManager.destroy();

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        destroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
