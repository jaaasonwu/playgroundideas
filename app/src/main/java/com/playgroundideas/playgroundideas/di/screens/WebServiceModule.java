package com.playgroundideas.playgroundideas.di.screens;

import com.playgroundideas.playgroundideas.datasource.remote.DesignWebservice;
import com.playgroundideas.playgroundideas.datasource.remote.UserWebservice;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Ferdinand on 16/09/2017.
 */

@Module
public class WebServiceModule {

    @Singleton
    @Provides
    UserWebservice provideUserWebService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.playgroundIdeas.org/user/").build().create(UserWebservice.class);
    }

    @Singleton
    @Provides
    DesignWebservice provideDesignWebService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.playgroundIdeas.org/user/").build().create(DesignWebservice.class);
    }
}
