package com.playgroundideas.playgroundideas.datasource.remote;

import com.playgroundideas.playgroundideas.domain.Plan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ferdinand on 9/09/2017.
 */

public interface PlanWebservice {

    @GET("/plan/{plan}")
    Call<Plan> getPlan(@Path("plan") long id);

}
