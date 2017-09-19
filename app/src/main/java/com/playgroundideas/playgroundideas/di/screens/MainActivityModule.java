package com.playgroundideas.playgroundideas.di.screens;

import com.playgroundideas.playgroundideas.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Ferdinand on 19/09/2017.
 */

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity contributeMainActivity();
}
