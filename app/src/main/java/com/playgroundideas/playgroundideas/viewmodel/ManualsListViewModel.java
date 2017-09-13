package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.ManualRepository;
import com.playgroundideas.playgroundideas.model.Manual;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class ManualsListViewModel extends ViewModel {

    private LiveData<List<Manual>> manualList;
    private ManualRepository manualRepository;

    @Inject
    public ManualsListViewModel(ManualRepository manualRepository) {
        this.manualRepository = manualRepository;
    }

    public void init(boolean downloaded) {
        if (manualList != null) {
            return;
        } else {
            manualList = (downloaded) ? manualRepository.getDownloaded() : manualRepository.getAll();
        }
    }

    public LiveData<List<Manual>> getManualList() {
        return manualList;
    }
}
