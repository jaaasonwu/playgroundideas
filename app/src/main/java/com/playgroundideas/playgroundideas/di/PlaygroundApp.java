package com.playgroundideas.playgroundideas.di;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by Ferdinand on 16/09/2017.
 */

public class PlaygroundApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

}
