package com.playgroundideas.playgroundideas.datasource.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginWebservice {
    @POST("wp/wordpress/index.php")
    Call<ResponseBody> authenticate(@Header("Authorization") String authHeader);
}
