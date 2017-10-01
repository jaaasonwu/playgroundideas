package com.playgroundideas.playgroundideas.datasource.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created by Ferdinand on 10/09/2017.
 */

public interface ManualWebservice {
    @Streaming
    @GET("/manuals/{manual}")
    Call<ResponseBody> getManual(@Path("manual") String manual);

    @Streaming
    @GET("/manuals/{manual}/{id}")
    Call<ResponseBody> getChapter(@Path("manual") String manual, @Path("id") int id);
}
