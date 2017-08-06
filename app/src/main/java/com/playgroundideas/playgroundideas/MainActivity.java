package com.playgroundideas.playgroundideas;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_designs:
                    mTextMessage.setText(R.string.designs);
                    return true;
                case R.id.navigation_manuals:
                    mTextMessage.setText(R.string.manuals);
                    return true;
                case R.id.navigation_plans:
                    mTextMessage.setText(R.string.plans);
                    return true;
                case R.id.navigation_projects:
                    mTextMessage.setText(R.string.projects);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Setting the color for different conditions for the nav bar
        ColorStateList navigationColor = getResources().getColorStateList(R.color.navigation);
        navigation.setItemIconTintList(navigationColor);
        navigation.setItemTextColor(navigationColor);

        // Use custom action bar
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mActionBarView = mInflater.inflate(R.layout.custom_actionbar, null);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setCustomView(mActionBarView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }

}
