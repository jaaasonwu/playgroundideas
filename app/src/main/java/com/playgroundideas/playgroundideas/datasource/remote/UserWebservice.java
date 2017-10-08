package com.playgroundideas.playgroundideas.datasource.remote;

import com.playgroundideas.playgroundideas.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ferdinand on 10/09/2017.
 */

public interface UserWebservice {

    @GET("/user/{user}")
    Call<User> getUser(@Path("user") long id);

    @GET("/user/{user}/version")
    Call<Long> getVersion(@Path("user") long id);

    @GET("/user/all")
    Call<List<User>> getAllUser();

    @GET("/user/all/version")
    Call<Map<Long, Long>> getVersionOfAll();

    @GET("/user/{user}/favourite-designs")
    Call<List<Long>> getFavouriteDesignsOf(@Path("user") long id);
}
