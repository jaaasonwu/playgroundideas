package com.playgroundideas.playgroundideas.di;

import android.app.Activity;

import com.playgroundideas.playgroundideas.MainActivity;
import com.playgroundideas.playgroundideas.di.screens.MainActivitySubComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by Ferdinand on 16/09/2017.
 */

@Module
public abstract class BuildersModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivityInjectorFactory(MainActivitySubComponent.Builder builder);

    // Add more bindings here for other sub components
}
