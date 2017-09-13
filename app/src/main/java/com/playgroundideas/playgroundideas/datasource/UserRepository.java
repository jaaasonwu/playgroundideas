package com.playgroundideas.playgroundideas.datasource;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.FavouritedDesignsPerUserDao;
import com.playgroundideas.playgroundideas.datasource.local.UserDao;
import com.playgroundideas.playgroundideas.datasource.remote.UserWebservice;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.FavouritedDesignsPerUser;
import com.playgroundideas.playgroundideas.model.User;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@javax.inject.Singleton
public class UserRepository {

    private final UserWebservice webservice;
    private final UserDao userDao;
    private final FavouritedDesignsPerUserDao favouriteDesignsDao;
    private final Executor executor;
    private final ProjectRepository projectRepository;
    private final DesignRepository designRepository;
    private final ManualRepository manualRepository;

    @Inject
    public UserRepository(UserWebservice webservice, UserDao userDao, Executor executor, ProjectRepository projectRepository, DesignRepository designRepository, ManualRepository manualRepository, FavouritedDesignsPerUserDao favouriteDesignsDao) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
        this.projectRepository = projectRepository;
        this.designRepository = designRepository;
        this.manualRepository = manualRepository;
        this.favouriteDesignsDao = favouriteDesignsDao;
    }

    public LiveData<User> getUser(Long id) {
        refreshUser(id);
        LiveData<User> userLiveData = userDao.load(id);
        // set the associated created designs
        userLiveData.getValue().setCreatedDesigns(designRepository.getAllCreatedBy(id).getValue());
        // set the associated favourited designs
        List<Design> favouritedDesigns = new LinkedList<>();
        for (FavouritedDesignsPerUser f : favouriteDesignsDao.loadAllOf(id).getValue()) {
            favouritedDesigns.add(designRepository.get(f.getDesignId()).getValue());
        }
        userLiveData.getValue().setFavouritedDesigns(favouritedDesigns);
        // set the associated created projects
        userLiveData.getValue().setCreatedProjects(projectRepository.getAllCreatedBy(id).getValue());

        return userLiveData;
    }


    private void refreshUser(final Long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if user was fetched recently
                boolean userExists = userDao.hasUser(id);
                if (!userExists) {
                    // refresh the data
                    //Response response = webservice.getUser(id).execute();
                    // TODO check for error etc.

                    //create user object from response.body
                    //User refreshedUser = new User();
                    // Update the database.The LiveData will automatically refresh so
                    // we don't need to do anything else here besides updating the database
                    //userDao.update(refreshedUser);
                }
            }
        });
    }

}
