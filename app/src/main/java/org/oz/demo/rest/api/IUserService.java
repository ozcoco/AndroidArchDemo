package org.oz.demo.rest.api;

import org.oz.demo.po.User;
import org.oz.demo.vo.UserVo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IUserService
{

    @GET("user/{userId}")
    Observable<User> user(@Path("userId") int userId);

    @GET("user/list/{page}")
    Observable<UserVo> users(@Path("page") int page);

    @GET("user/list/{page}")
    Call<UserVo> users2(@Path("page") int page);

}
