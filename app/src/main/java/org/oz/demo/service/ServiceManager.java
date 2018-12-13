package org.oz.demo.service;

import android.content.Context;

import androidx.annotation.NonNull;

import org.oz.demo.rest.IWebservice;
import org.oz.demo.rest.WebService;
import org.oz.demo.sql.ISQLService;
import org.oz.demo.sql.SQLService;

public enum ServiceManager implements IServiceManager {

    INSTANCE {
        @Override
        public IWebservice getWebservice() {

            return WebService.INSTANCE;
        }


        @Override
        public ISQLService getSQLService() {

            return SQLService.INSTANCE;
        }

        @Override
        public void _init(@NonNull Context context) {

            SQLService.init(context);
        }

        @Override
        public void _destroy() {
            SQLService.destroy();
        }
    };

    public static ServiceManager getInstance() {

        return INSTANCE;
    }

    public static void init(@NonNull Context context) {

        INSTANCE._init(context);
    }

    public static void destroy() {

        INSTANCE._destroy();

    }

    public abstract void _init(@NonNull Context context);

    public abstract void _destroy();

}
