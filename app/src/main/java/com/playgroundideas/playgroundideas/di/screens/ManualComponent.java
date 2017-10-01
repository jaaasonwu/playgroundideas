package com.playgroundideas.playgroundideas.di.screens;

import com.playgroundideas.playgroundideas.datasource.remote.ManualWebservice;
import com.playgroundideas.playgroundideas.manuals.ManualDownloadHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jason Wu on 10/1/2017.
 */

@Singleton
@Component(modules = {WebServiceModule.class})
public interface ManualComponent {
    ManualWebservice provideManualWebService();

    void inject(ManualDownloadHelper downloadHelper);
}
