package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.DesignRepository;
import com.playgroundideas.playgroundideas.model.Design;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class DesignViewModel extends ViewModel {

    private LiveData<Design> design;
    private DesignRepository designRepository;

    @Inject
    public DesignViewModel(DesignRepository designRepository) {
        this.designRepository = designRepository;
    }

    public void init(Long designId) {
        if(design != null) {
            return;
        } else {
            design = designRepository.get(designId);
        }
    }

    public LiveData<Design> getDesign() {
        return this.design;
    }
}
