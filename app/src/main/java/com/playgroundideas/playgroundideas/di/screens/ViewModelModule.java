package com.playgroundideas.playgroundideas.di.screens;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.playgroundideas.playgroundideas.viewmodel.DesignListViewModel;
import com.playgroundideas.playgroundideas.viewmodel.DesignViewModel;
import com.playgroundideas.playgroundideas.viewmodel.ManualViewModel;
import com.playgroundideas.playgroundideas.viewmodel.ManualsListViewModel;
import com.playgroundideas.playgroundideas.viewmodel.ProjectListViewModel;
import com.playgroundideas.playgroundideas.viewmodel.ProjectViewModel;
import com.playgroundideas.playgroundideas.viewmodel.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DesignViewModel.class)
    abstract ViewModel bindDesignViewModel(DesignViewModel designViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DesignListViewModel.class)
    abstract ViewModel bindDesignListViewModel(DesignListViewModel designListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ManualsListViewModel.class)
    abstract ViewModel bindManualsListViewModel(ManualsListViewModel manualsListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ManualViewModel.class)
    abstract ViewModel bindManualViewModel(ManualViewModel manualViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProjectViewModel.class)
    abstract ViewModel bindProjectViewModel(ProjectViewModel projectViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProjectListViewModel.class)
    abstract ViewModel bindProjectListViewModel(ProjectListViewModel projectListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PlaygroundViewModelFactory factory);
}
