package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.ManualRepository;
import com.playgroundideas.playgroundideas.model.Manual;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class ManualViewModel extends ViewModel {

    private LiveData<Manual> manual;
    private ManualRepository manualRepository;

    @Inject
    public ManualViewModel(ManualRepository manualRepository) {
        this.manualRepository = manualRepository;
    }

    public void init(Long manualId) {
        if (manual != null) {
            return;
        } else {
            manual = manualRepository.getManual(manualId);
        }
    }

    public LiveData<Manual> getManual() {
        return manual;
    }
}
