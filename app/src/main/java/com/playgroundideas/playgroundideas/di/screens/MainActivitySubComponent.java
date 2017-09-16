package com.playgroundideas.playgroundideas.di.screens;

import com.playgroundideas.playgroundideas.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Ferdinand on 16/09/2017.
 */

@Subcomponent
public interface MainActivitySubComponent extends AndroidInjector<MainActivity>{

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }
}
