package com.playgroundideas.playgroundideas.di;

import android.app.Application;
import android.content.Context;

import com.playgroundideas.playgroundideas.di.screens.DatabaseModule;
import com.playgroundideas.playgroundideas.di.screens.ViewModelModule;
import com.playgroundideas.playgroundideas.di.screens.WebServiceModule;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ferdinand on 16/09/2017.
 */

@Module(includes = {WebServiceModule.class, DatabaseModule.class, ViewModelModule.class})
public class AppModule {

    @Provides Context provideApplicationContext(Application application) {
        return application;
    }

    @Singleton
    @Provides
    Executor provideExecutor() {
        return Executors.newCachedThreadPool();
    }

}
