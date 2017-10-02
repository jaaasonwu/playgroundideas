package com.playgroundideas.playgroundideas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.playgroundideas.playgroundideas.datasource.remote.LoginWebservice;
import com.playgroundideas.playgroundideas.di.screens.DaggerLoginComponent;
import com.playgroundideas.playgroundideas.di.screens.LoginComponent;
import com.playgroundideas.playgroundideas.login.LoginActivity;

import javax.inject.Inject;

/**
 * Created by Jason Wu on 9/30/2017.
 */

public class AccountInfoActivity extends AppCompatActivity {
    public static final String[] mCountries = {"Australia","American","South Africa","China"};

    private Button mSaveButton;
    private Button mLogoutButton;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private Spinner mCountry;
    private EditText mPaypal;
    private LoginComponent mLoginComponent;
    @Inject
    LoginWebservice mLoginWebservice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        mLoginComponent = DaggerLoginComponent.builder().build();
        mLoginComponent.inject(this);

        mSaveButton = findViewById(R.id.save_button);
        mLogoutButton = findViewById(R.id.logout_button);
        mSaveButton.setOnClickListener(new OnSaveClick());
        mLogoutButton.setOnClickListener(new OnLogoutClick(this));

        mFirstName = findViewById(R.id.firstname_text);
        mLastName = findViewById(R.id.lastname_text);
        mEmail = findViewById(R.id.email_text);
        mCountry = findViewById(R.id.spinner_countires);
        mPaypal = findViewById(R.id.paypal_email);
        setUpSpinner();
    }

    private void setUpSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,mCountries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(adapter);
        mCountry.setSelection(0);
    }
    private class OnSaveClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mLoginWebservice.updateInfo(mFirstName.getText().toString(),
                    mLastName.getText().toString(),
                    mEmail.getText().toString(),
                    (String)mCountry.getSelectedItem(),
                    mPaypal.getText().toString());
        }
    }

    private class OnLogoutClick implements View.OnClickListener {
        private Context mContext;
        public OnLogoutClick(Context context) {
            mContext = context;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
    }
}
