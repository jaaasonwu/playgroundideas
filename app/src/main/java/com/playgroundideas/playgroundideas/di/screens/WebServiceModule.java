package com.playgroundideas.playgroundideas.di.screens;

import com.playgroundideas.playgroundideas.datasource.remote.DesignWebservice;
import com.playgroundideas.playgroundideas.datasource.remote.LoginWebservice;
import com.playgroundideas.playgroundideas.datasource.remote.ManualWebservice;
import com.playgroundideas.playgroundideas.datasource.remote.ProjectWebservice;
import com.playgroundideas.playgroundideas.datasource.remote.UserWebservice;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class WebServiceModule {

    @Singleton
    @Provides
    UserWebservice provideUserWebService() {
        return new Retrofit.Builder()
                .baseUrl("http://swen90014v-2017plp.cis.unimelb.edu.au:3000").addConverterFactory(GsonConverterFactory.create()).build().create(UserWebservice.class);
    }

    @Singleton
    @Provides
    DesignWebservice provideDesignWebService() {
        return new Retrofit.Builder()
                .baseUrl("http://swen90014v-2017plp.cis.unimelb.edu.au:3000").addConverterFactory(GsonConverterFactory.create()).build().create(DesignWebservice.class);
    }

    @Singleton
    @Provides
    ProjectWebservice provideProjectWebService() {
        return new Retrofit.Builder()
                .baseUrl("http://swen90014v-2017plp.cis.unimelb.edu.au:3000").addConverterFactory(GsonConverterFactory.create()).build().create(ProjectWebservice.class);
    }

    @Singleton
    @Provides
    ManualWebservice provideManualWebService() {
        return new Retrofit.Builder()
                .baseUrl("http://swen90014v-2017plp.cis.unimelb.edu.au:3000").addConverterFactory(GsonConverterFactory.create()).build().create(ManualWebservice.class);
    }

    @Singleton
    @Provides
    LoginWebservice provideLoginWebService() {
        return new Retrofit.Builder()
                .baseUrl("http://swen90014v-2017plp.cis.unimelb.edu.au").addConverterFactory(GsonConverterFactory.create()).build().create(LoginWebservice.class);
    }
}
