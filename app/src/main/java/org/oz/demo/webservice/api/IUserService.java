package org.oz.demo.webservice.api;

import org.oz.demo.po.Message;
import org.oz.demo.po.User;
import org.oz.demo.vo.UserVo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IUserService {

    @GET("user/{userId}")
    Observable<User> user(@Path("userId") int userId);

    @GET("user/list/{page}")
    Observable<UserVo> users(@Path("page") int page);

    @GET("user/list2/{page}")
    Observable<Message<List<User>>> users2(@Path("page") int page);

}
