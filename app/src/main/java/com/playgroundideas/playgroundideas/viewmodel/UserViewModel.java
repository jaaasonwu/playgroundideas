package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.UserRepository;
import com.playgroundideas.playgroundideas.model.User;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class UserViewModel extends ViewModel {

    private LiveData<User> user;
    private UserRepository userRepository;

    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void init(Long userId) {
        if(user != null) {
            return;
        } else {
            user = userRepository.getUser(userId);
        }
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
