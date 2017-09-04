package com.playgroundideas.playgroundideas.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.playgroundideas.playgroundideas.R;


/**
 * An activity for login an
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(R.string.action_sign_in);
        setContentView(R.layout.activity_login);
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        Fragment loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.login_container, loginFragment).commit();
    }

    private void setOnSignInListener() {

    }
}

