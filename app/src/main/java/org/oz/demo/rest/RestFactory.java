package org.oz.demo.rest;

import androidx.annotation.NonNull;

import org.oz.demo.network.HttpUtils;

class RestFactory {

    @NonNull
    static <T> T getWebService(Class<T> serviceClass) {
        return HttpUtils.INSTANCE.getRetrofit().create(serviceClass);
    }

}
