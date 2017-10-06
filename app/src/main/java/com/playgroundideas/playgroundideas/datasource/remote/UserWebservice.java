package com.playgroundideas.playgroundideas.datasource.remote;

import com.playgroundideas.playgroundideas.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ferdinand on 10/09/2017.
 */

public interface UserWebservice {

    @GET("/user/{user}")
    Call<User> getUser(@Path("user") long id);

    @GET("/user/{user}")
    Call<Long> getVersion(@Path("user") long id);
}
