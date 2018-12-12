package org.oz.demo.network;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public enum HttpUtils
{

    INSTANCE;

    private static final int TIME_OUT = 4;
    private static final int CONNECT_TIMEOUT = 15;

    @NonNull
    private final Retrofit retrofit;


    @NonNull
    public Retrofit getRetrofit()
    {
        return retrofit;
    }

    private final static String BASE_URL = "http://192.168.0.103:8080/";


    private HttpUtils()
    {
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

    }
}
