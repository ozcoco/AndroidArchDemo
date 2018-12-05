package org.oz.demo.webservice.api;

import org.oz.demo.webservice.IService;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ITestService extends IService
{

    @GET("test")
    Observable<String> test();

}
