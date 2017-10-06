package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.local.UserDao;
import com.playgroundideas.playgroundideas.datasource.remote.UserWebservice;
import com.playgroundideas.playgroundideas.model.User;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Response;

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
                // check if there is a network connection
                if(isNetworkAvailable()) {
                    // check if the date has changed on the server using a version number
                    long remoteVersion = webservice.getVersion(id).execute().body().longValue();
                    long localVersion = (userDao.load(id) == null) ? 0 : userDao.load(id).getValue().getVersion();
                    if (remoteVersion > localVersion) {
                        // refresh the local data
                        // retrieve the data from the remote data source
                        Response response = webservice.getUser(id).execute();

                        //create new object from response.body
                        User newUser = deserialiseUser(response.body());
                        // Update the database. The LiveData will automatically refresh so
                        // we don't need to do anything else here besides updating the database
                        if(localVersion == 0) {
                            userDao.insertUser(newUser);
                        } else {
                            userDao.update(newUser);
                        }
                    }
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private User deserialiseUser(Object response) {
        //TODO implement
    }

}
