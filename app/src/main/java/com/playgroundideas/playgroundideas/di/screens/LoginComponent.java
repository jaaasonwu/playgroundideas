package com.playgroundideas.playgroundideas.di.screens;

import com.playgroundideas.playgroundideas.AccountInfoActivity;
import com.playgroundideas.playgroundideas.datasource.remote.UserWebservice;
import com.playgroundideas.playgroundideas.login.LoginFragment;
import com.playgroundideas.playgroundideas.login.SignUpFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jason Wu on 10/2/2017.
 */

@Singleton
@Component(modules = {WebServiceModule.class})
public interface LoginComponent {
    UserWebservice getLoginWebservice();

    void inject(AccountInfoActivity accountInfoActivity);

    void inject(LoginFragment loginFragment);

    void inject(SignUpFragment signUpFragment);
}
