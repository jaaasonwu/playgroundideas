package com.playgroundideas.playgroundideas.datasource.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginWebservice {
    @POST("wp/wordpress/index.php")
    Call<ResponseBody> authenticate(@Header("Authorization") String authHeader);

    @POST("/user/signup")
    Call<ResponseBody> signup(@Field("email") String email);

    @POST("user/forget_password")
    Call<ResponseBody> forgetPassword(@Field("user") String email);

    @POST("user/update")
    Call<ResponseBody> updateInfo(@Field("firstName") String firstName,
                                  @Field("surname") String surname,
                                  @Field("email") String email,
                                  @Field("Country") String country,
                                  @Field("payPal") String payPal);
}
