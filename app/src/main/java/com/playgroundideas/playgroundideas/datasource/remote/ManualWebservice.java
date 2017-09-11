package com.playgroundideas.playgroundideas.datasource.remote;

import com.playgroundideas.playgroundideas.domain.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ferdinand on 10/09/2017.
 */

public interface ManualWebservice {

    @GET("/manual/{manual}")
    Call<User> getManual(@Path("manual") long id);
}
