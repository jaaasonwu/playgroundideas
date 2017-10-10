package com.playgroundideas.playgroundideas.datasource.remote;

import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualChapter;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ferdinand on 10/09/2017.
 */

public interface ManualWebservice {

    @GET("/manual/{manual}")
    Call<Manual> getManual(@Path("manual") long id);

    @GET("/manual/{manual}/version")
    Call<Long> getVersion(@Path("manual") long id);

    @GET("/manual/all")
    Call<List<Manual>> getAllManual();

    @GET("/manual/all/version")
    Call<Map<Long, Long>> getVersionOfAll();

    @GET("/manual/{manualId}/pdf")
    Call<ResponseBody> getPdfOf(@Path("manualId") long manualId);

    @GET("/manual/{manualId}/chapter/all")
    Call<List<ManualChapter>> getChaptersOf(@Path("manualId") long manualId);
}
