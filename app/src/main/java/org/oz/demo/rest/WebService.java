package org.oz.demo.rest;


import android.content.Context;

import androidx.annotation.NonNull;

import org.oz.demo.rest.api.IUserService;

public enum WebService implements IWebservice {

    INSTANCE {
        @Override
        public void _init(@NonNull Context context) {

        }

        @Override
        public void _destroy() {

        }

        private IUserService userService;

        @Override
        public IUserService getUserService() {
            if (userService == null)
                userService = RestFactory.getWebService(IUserService.class);

            return userService;
        }
    };

    public static WebService getInstance() {
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
