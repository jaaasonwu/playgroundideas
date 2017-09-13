package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.DesignRepository;
import com.playgroundideas.playgroundideas.model.Design;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class DesignListViewModel extends ViewModel {

    private LiveData<List<Design>> designList;
    private DesignRepository designRepository;

    @Inject
    public DesignListViewModel(DesignRepository designRepository) {
        this.designRepository = designRepository;
    }

    /*
     * Setting userId to null will cause all designs to be loaded.
     * Specifying an existing userId causes all designs favourited by that identified user to be loaded
     */
    public void init(Long userId) {
        if (designList != null) {
            return;
        } else {
            designList = (userId != null) ? designRepository.getFavouritesOf(userId) : designRepository.getAll();
        }
    }

    public LiveData<List<Design>> getDesignList() {
        return designList;
    }
}
