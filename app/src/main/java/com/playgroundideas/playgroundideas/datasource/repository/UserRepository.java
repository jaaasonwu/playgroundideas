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

    public void createUser(User user) {
        userDao.insertUser(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void setCurrentUser(Long userId) {
        refreshUser(userId);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(R.string.saved_user + "", userId);
        editor.apply();
    }

    public Long getCurrentUser() {
        return sharedPreferences.getLong(R.string.saved_user + "", 0);
    }


    private void refreshUser(final Long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if there is internet connection
                // check if the user hasn't been loaded recently
                // check if the user has changed on the server using a version number/timestamp
                // -> retrieve user from server and update/insert it in the local database
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
