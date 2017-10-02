package com.playgroundideas.playgroundideas.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.remote.LoginWebservice;
import com.playgroundideas.playgroundideas.di.screens.DaggerLoginComponent;
import com.playgroundideas.playgroundideas.di.screens.LoginComponent;

import javax.inject.Inject;

public class SignUpFragment extends Fragment {
    // UI references.
    private View mRootView;
    private TextView mEmailView;
    private View mSignupFormView;
    private Button mSignupButton;
    private LoginComponent mLoginComponent;
    @Inject
    LoginWebservice mLoginWebservice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginComponent = DaggerLoginComponent.builder().build();
        mLoginComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_signup, container, false);
        mEmailView = mRootView.findViewById(R.id.signup_email);
        mSignupButton = mRootView.findViewById(R.id.email_sign_in_button);
        mSignupButton.setOnClickListener(new OnSignupClick());
        mSignupFormView = mRootView.findViewById(R.id.signup_form);

        getActivity().setTitle(R.string.action_sign_up);
        setSwitchLoginListener();
        return mRootView;
    }

    private void setSwitchLoginListener() {
        TextView switchLogin = mRootView.findViewById(R.id.switch_login);
        switchLogin.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment loginFragment = new LoginFragment();
                fragmentTransaction.replace(R.id.login_container, loginFragment).commit();
            }
        });
    }

    private class OnSignupClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String email = mEmailView.getText().toString();
            mLoginWebservice.signup(email);
        }
    }
}
