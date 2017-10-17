package com.playgroundideas.playgroundideas.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.MainActivity;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.remote.LoginWebservice;
import com.playgroundideas.playgroundideas.datasource.remote.UserWebservice;
import com.playgroundideas.playgroundideas.datasource.repository.UserRepository;
import com.playgroundideas.playgroundideas.model.User;

import java.io.IOException;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A fragment to handle login
 */
public class LoginFragment extends DaggerFragment {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private View mRootView;
    private TextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mLoginButton;
    private TextView mForget;
    @Inject
    LoginWebservice webservice;
    @Inject
    UserWebservice userWebservice;
    //TODO replace this with user view model
    @Inject
    UserRepository userRepository;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_login, container, false);
        mEmailView = mRootView.findViewById(R.id.email);
        mPasswordView = mRootView.findViewById(R.id.password);
        // Check the validation of the password when changed
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // Set the action when login button is clicked
        mLoginButton = mRootView.findViewById(R.id.email_sign_in_button);
        mLoginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = mRootView.findViewById(R.id.login_form);
        mProgressView = mRootView.findViewById(R.id.login_progress);
        mForget = mRootView.findViewById(R.id.forget_password);
        mForget.setOnClickListener(new OnForgetClick());
        // Set up the listener to switch to sign up fragment
        setSwitchSignupListener();
        getActivity().setTitle(R.string.action_log_in);

        return mRootView;
    }

    /**
     * The method set the action to switch to sign up fragment
     */
    private void setSwitchSignupListener() {
        TextView switchSignup = mRootView.findViewById(R.id.switch_signup);
        switchSignup.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment signupFragment = new SignUpFragment();
                fragmentTransaction.replace(R.id.login_container, signupFragment).commit();
            }
        });
    }

    private class OnForgetClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String email = mEmailView.getText().toString();
            webservice.forgetPassword(email);
        }
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, getActivity());
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Check the format of emails
     */
    private boolean isEmailValid(String email) {
        return true;
    }


    /**
     * Check the length of the password
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private Context mContext;

        UserLoginTask(String email, String password, Context context) {
            mEmail = email;
            mPassword = password;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Set up the request for basic authentication
            String info = mEmail + ":" + mPassword;
            String authHeader = "Basic " + Base64.encodeToString(info.getBytes(), Base64.NO_WRAP);
            Call<ResponseBody> call = webservice.authenticate(authHeader);

            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    User user = userWebservice.getUser(1).execute().body();
                    userRepository.createUser(user);
                    userRepository.setCurrentUser(user.getId());
                    return true;
                } else {
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (!success) {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            } else {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
