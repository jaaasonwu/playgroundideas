package com.playgroundideas.playgroundideas.di.screens;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.playgroundideas.playgroundideas.viewmodel.DesignListViewModel;
import com.playgroundideas.playgroundideas.viewmodel.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Ferdinand on 15/09/2017.
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DesignListViewModel.class)
    abstract ViewModel bindDesignListViewModel(DesignListViewModel designListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PlaygroundViewModelFactory factory);
}
