package org.oz.demo.network;

import androidx.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public enum HttpUtils {

    INSTANCE;

    private static final int TIME_OUT = 4;
    private static final int CONNECT_TIMEOUT = 15;

    @NonNull
    private final Retrofit retrofit;


    @NonNull
    public Retrofit getRetrofit() {
        return retrofit;
    }

    private final static String BASE_URL = "http://192.168.1.109:8080/";


    HttpUtils() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor()).connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

    }
}
