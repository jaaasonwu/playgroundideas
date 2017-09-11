package com.playgroundideas.playgroundideas.datasource;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.UserDao;
import com.playgroundideas.playgroundideas.datasource.remote.UserWebservice;
import com.playgroundideas.playgroundideas.domain.User;

import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@javax.inject.Singleton
public class UserRepository {

    private final UserWebservice webservice;
    private final UserDao userDao;
    private final Executor executor;

    @Inject
    public UserRepository(UserWebservice webservice, UserDao userDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<User> getUser(long id) {
        refreshUser(id);
        // return a LiveData directly from the database.
        return userDao.load(id);
    }


    private void refreshUser(final long id) {
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
