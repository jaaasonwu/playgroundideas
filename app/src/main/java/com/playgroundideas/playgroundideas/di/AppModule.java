package com.playgroundideas.playgroundideas.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.di.screens.DatabaseModule;
import com.playgroundideas.playgroundideas.di.screens.FragmentBuilderModule;
import com.playgroundideas.playgroundideas.di.screens.ViewModelModule;
import com.playgroundideas.playgroundideas.di.screens.WebServiceModule;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(includes = {FragmentBuilderModule.class, WebServiceModule.class, DatabaseModule.class, ViewModelModule.class})
class AppModule {

    @Provides
    Context provideApplicationContext(Application application) {
        return application;
    }

    @Provides
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(R.string.preference_file_key + "", Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    Executor provideExecutor() {
        return Executors.newCachedThreadPool();
    }

}
