package com.playgroundideas.playgroundideas.datasource.remote;

import com.playgroundideas.playgroundideas.model.Project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ferdinand on 10/09/2017.
 */

public interface ProjectWebservice {

    @GET("/project/{project}")
    Call<Project> getProject(@Path("project") long id);
}
