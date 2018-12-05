package org.oz.demo.webservice.api;

import org.oz.demo.po.User;
import org.oz.demo.webservice.IService;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IUserService extends IService {

    @GET("user/{userId}")
    Observable<User> user(@Path("userId") int userId);

}
