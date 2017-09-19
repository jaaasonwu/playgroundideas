package com.playgroundideas.playgroundideas.di.screens;

import com.playgroundideas.playgroundideas.designs.DesignFavoriteList;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Ferdinand on 17/09/2017.
 */

@Module
public abstract class FragmentBuilderModule {

    //@ContributesAndroidInjector
    //abstract DesignsFragment contributeDesignsFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract DesignFavoriteList contributeDesignFavoriteList();
}
