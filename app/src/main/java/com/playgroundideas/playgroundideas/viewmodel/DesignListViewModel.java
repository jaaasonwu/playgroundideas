package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.repository.DesignRepository;
import com.playgroundideas.playgroundideas.datasource.repository.UserRepository;
import com.playgroundideas.playgroundideas.model.Design;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class DesignListViewModel extends ViewModel {

    private LiveData<List<Design>> designList;
    private DesignRepository designRepository;
    private UserRepository userRepository;

    @Inject
    public DesignListViewModel(DesignRepository designRepository, UserRepository userRepository) {
        this.designRepository = designRepository;
        this.userRepository = userRepository;
    }

    public void init(boolean favouritedOnly) {
        if (designList != null) {
            return;
        } else {
            designList = (favouritedOnly) ? designRepository.getFavouritesOf(userRepository.readCurrentUser()) : designRepository.getAll();
        }
    }

    public LiveData<List<Design>> getDesignList() {
        return designList;
    }
}
