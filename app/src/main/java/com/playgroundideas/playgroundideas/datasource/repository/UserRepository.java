package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.local.UserDao;
import com.playgroundideas.playgroundideas.datasource.remote.UserWebservice;
import com.playgroundideas.playgroundideas.model.User;

import java.util.concurrent.Executor;

import javax.inject.Inject;

@javax.inject.Singleton
public class UserRepository {

    private final UserWebservice webservice;
    private final UserDao userDao;
    private final Executor executor;
    private final SharedPreferences sharedPreferences;

    @Inject
    public UserRepository(UserWebservice webservice, UserDao userDao, Executor executor, SharedPreferences sharedPreferences) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
        this.sharedPreferences = sharedPreferences;
    }

    public LiveData<User> getUser(Long id) {
        refreshUser(id);
        return userDao.load(id);
    }

    public void writeCurrentUser(Long userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(R.string.saved_user + "", userId);
        editor.apply();
    }

    public Long readCurrentUser() {
        return sharedPreferences.getLong(R.string.saved_user + "", 0);
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
