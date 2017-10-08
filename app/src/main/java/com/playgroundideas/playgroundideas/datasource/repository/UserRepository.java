package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.local.UserDao;
import com.playgroundideas.playgroundideas.datasource.remote.UserWebservice;
import com.playgroundideas.playgroundideas.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;

@Singleton
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

    public LiveData<List<User>> getUsers() {
        refreshAllUsers();
        return userDao.loadAll();
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

                        webservice.getVersion(id).enqueue(new Callback<Long>() {
                            @Override
                            public void onResponse(Call<Long> call, Response<Long> response) {
                                // check if the date has changed on the server using a version number
                                final long remoteVersion = response.body();
                                final long localVersion = userDao.getVersionOf(id);

                                if (remoteVersion > localVersion) {
                                    // refresh the local data
                                    // retrieve the data from the remote data source
                                    webservice.getUser(id).enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            //create new object from response.body
                                            User newUser = response.body();
                                            // Update the database. The LiveData will automatically refresh so
                                            // we don't need to do anything else here besides updating the database
                                            if (localVersion == 0) {
                                                userDao.insertUser(newUser);
                                            } else {
                                                userDao.update(newUser);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable throwable) {
                                            // do nothing
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<Long> call, Throwable throwable) {
                                // do nothing
                            }
                        });
                }
            }
        });
    }

    private void refreshAllUsers() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if there is a network connection
                if(isNetworkAvailable()) {

                        // check if any data has changed on the server
                        webservice.getVersionOfAll().enqueue(new Callback<Map<Long, Long>>() {
                            @Override
                            public void onResponse(Call<Map<Long, Long>> call, Response<Map<Long, Long>> response) {
                                for (Map.Entry<Long, Long> remote : response.body().entrySet()) {
                                    final long localVersion = userDao.getVersionOf(id);
                                    if (remote.getValue() > localVersion) {
                                        // refresh if data has changed
                                        refreshUser(remote.getKey());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Map<Long, Long>> call, Throwable throwable) {
                                // do nothing
                            }
                        });
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        // TODO implement internet connection check
        //ConnectivityManager connectivityManager
        //        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return true;
    }

}
