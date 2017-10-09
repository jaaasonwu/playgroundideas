package com.playgroundideas.playgroundideas.datasource.remote;

import com.playgroundideas.playgroundideas.model.Design;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ferdinand on 10/09/2017.
 */

public interface DesignWebservice {

    @GET("/design/{design}")
    Call<Design> getDesign(@Path("design") long id);

    @GET("/design/{design}/version")
    Call<Long> getVersion(@Path("design") long id);

    @GET("/design/all")
    Call<List<Design>> getAllDesign();

    @GET("/design/all/version")
    Call<Map<Long, Long>> getVersionOfAll();

    @GET("/design/{design}/image/all")
    Call<List<String>> getImageIdsOf(@Path("design") long id);

    @GET("/design/{designId}/image/{imageId}")
    Call<ResponseBody> getImage(@Path("designId") long designId, @Path("imageId") String imageId);
}
