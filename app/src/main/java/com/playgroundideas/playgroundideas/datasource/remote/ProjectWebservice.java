package com.playgroundideas.playgroundideas.datasource.remote;

import com.playgroundideas.playgroundideas.model.Project;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ferdinand on 10/09/2017.
 */

public interface ProjectWebservice {

    @GET("/project/{project}")
    Call<Project> getProject(@Path("project") long id);

    @GET("/project/{project}/version")
    Call<Long> getVersion(@Path("project") long id);

    @GET("/project/all")
    Call<List<Project>> getAllProject();

    @GET("/project/all/version")
    Call<Map<Long, Long>> getVersionOfAll();

    @GET("/project/{project}/image/all")
    Call<List<String>> getImageIdsOf(@Path("project") long id);

    @GET("/project/{projectId}/image/{imageId}")
    Call<ResponseBody> getImage(@Path("projectId") long projectId, @Path("imageId") String imageId);
}
