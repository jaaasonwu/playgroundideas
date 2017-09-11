package com.playgroundideas.playgroundideas.datasource.remote;

import com.playgroundideas.playgroundideas.domain.Design;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ferdinand on 10/09/2017.
 */

public interface DesignWebservice {

    @GET("/design/{design}")
    Call<Design> getDesign(@Path("design") long id);
}
