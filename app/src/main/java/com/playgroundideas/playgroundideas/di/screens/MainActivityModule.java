package com.playgroundideas.playgroundideas.di.screens;

import android.app.Activity;

import com.playgroundideas.playgroundideas.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by Ferdinand on 16/09/2017.
 */

@Module(subcomponents = {MainActivitySubComponent.class})
public abstract class MainActivityModule  {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindMainActivityInjectorFactory(MainActivitySubComponent.Builder builder);
}
