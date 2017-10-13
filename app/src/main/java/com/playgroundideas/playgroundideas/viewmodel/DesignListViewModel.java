package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;

import com.playgroundideas.playgroundideas.datasource.repository.DesignRepository;
import com.playgroundideas.playgroundideas.datasource.repository.UserRepository;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.User;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class DesignListViewModel extends ViewModel {

    private LiveData<List<Pair<Design, Boolean>>> designList;
    private DesignRepository designRepository;
    private UserRepository userRepository;

    @Inject
    public DesignListViewModel(DesignRepository designRepository, UserRepository userRepository) {
        this.designRepository = designRepository;
        this.userRepository = userRepository;
    }

    public void markAsFavourite(Design design, boolean isFavourite) {
        User user = userRepository.getUser(userRepository.getCurrentUser()).getValue();
        if(designRepository.isFavouriteOf(design, user)) {
            if(!isFavourite) {
                designRepository.addFavourite(design, user);
            }
        } else if (isFavourite) {
            designRepository.removeFavourite(design, user);
        }
    }

    public void init(boolean favouritedOnly) {

        if (designList != null) {
            return;
        } else {
            User user = userRepository.getUser(userRepository.getCurrentUser()).getValue();
            designList = (favouritedOnly) ? designRepository.getFavouritesOf(user) : designRepository.getAllOf(user);
        }
    }

    public LiveData<List<Pair<Design, Boolean>>> getDesignList() {
        return designList;
    }
}
