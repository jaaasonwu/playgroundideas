package com.playgroundideas.playgroundideas.di.screens;

import com.playgroundideas.playgroundideas.designs.DesignBrowseList;
import com.playgroundideas.playgroundideas.designs.DesignFavoriteList;
import com.playgroundideas.playgroundideas.login.LoginFragment;
import com.playgroundideas.playgroundideas.manuals.ManualExpandableList;
import com.playgroundideas.playgroundideas.manuals.ManualsOfflineList;
import com.playgroundideas.playgroundideas.projects.ProjectBrowser;
import com.playgroundideas.playgroundideas.projects.ProjectMy;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract DesignFavoriteList contributeDesignFavoriteList();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract DesignBrowseList contributeDesignBrowseList();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ManualsOfflineList contributeManualsOfflineList();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ManualExpandableList contributeManualExpandableList();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProjectMy contributeProjectMy();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProjectBrowser contributeProjectBrowser();

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();
}
