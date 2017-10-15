package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;

import com.playgroundideas.playgroundideas.datasource.repository.DesignRepository;
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

    @Inject
    public DesignListViewModel(DesignRepository designRepository) {
        this.designRepository = designRepository;
    }

    public void markAsFavourite(Design design, User user, boolean isFavourite) {
        if(designRepository.isFavouriteOf(design, user)) {
            if(!isFavourite) {
                designRepository.addFavourite(design, user);
            }
        } else if (isFavourite) {
            designRepository.removeFavourite(design, user);
        }
    }

    public void init(boolean favouritedOnly, User user) {
        if(designList != null) {
            return;
        } else {
            designList = (favouritedOnly) ? designRepository.getFavouritesOf(user) : designRepository.getAllOf(user);
        }
    }

    public LiveData<List<Pair<Design, Boolean>>> getDesignList() {
        return designList;
    }
}
