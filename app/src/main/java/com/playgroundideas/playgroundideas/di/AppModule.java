package com.playgroundideas.playgroundideas.di;

import android.content.Context;

import com.playgroundideas.playgroundideas.di.screens.MainActivitySubComponent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ferdinand on 16/09/2017.
 */

@Module(subcomponents = {MainActivitySubComponent.class})
public abstract class AppModule {

    @Provides
    Context provideContext(PlaygroundApp application) {
        return application.getApplicationContext();
    }

}
