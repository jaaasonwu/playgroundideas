package com.playgroundideas.playgroundideas;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private LayoutInflater mInflater;
    private ViewPager mViewPager;
    private ArrayList<PagerAdapter> mAdapters;
    private int mCurrentFragment;

    static final int NUM_BOTTOM_TABS = 4;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * This method defines the behavior when a different bottom navigation item is selected
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            boolean success = false;
            switch (item.getItemId()) {
                case R.id.navigation_designs:
                    mCurrentFragment = 0;
                    success = true;
                    break;
                case R.id.navigation_plans:
                    mCurrentFragment = 1;
                    success = true;
                    break;
                case R.id.navigation_manuals:
                    mCurrentFragment = 2;
                    success = true;
                    break;
                case R.id.navigation_projects:
                    mCurrentFragment = 3;
                    success = true;
                    break;
            }
            if (success) {
                mViewPager.setAdapter(mAdapters.get(mCurrentFragment));
                return true;
            } else {
                return false;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // Setting the color for different conditions for the nav bar
        ColorStateList navigationColor = getResources().getColorStateList(R.color.navigation);
        navigation.setItemIconTintList(navigationColor);
        navigation.setItemTextColor(navigationColor);


        // Use custom action bar
        mInflater = LayoutInflater.from(this);
        View mActionBarView = mInflater.inflate(R.layout.custom_actionbar, null);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setCustomView(mActionBarView);
        mActionBar.setDisplayShowCustomEnabled(true);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        TextView settingButton = (TextView) findViewById(R.id.setting_button);
        TextView createButton = (TextView) findViewById(R.id.create_button);
        settingButton.setTypeface(iconFont);
        createButton.setTypeface(iconFont);

        //Define the top tabs
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.fav_designs));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.brs_designs));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        mViewPager = (ViewPager) findViewById(R.id.main_pager);
        // Create one adapter for each bottom tab
        mAdapters = new ArrayList<>(4);
        // Define all the tab titles
        CharSequence[][] tabTitles = new CharSequence[][]{
                {getText(R.string.fav_designs), getText(R.string.brs_designs)},
                {getText(R.string.my_plans), getText(R.string.brs_plans)},
                {getText(R.string.manuals), getText(R.string.offline_manuals)},
                {getText(R.string.my_projects), getText(R.string.brs_projects)}};
        for (int i = 0; i < NUM_BOTTOM_TABS; i++) {
            mAdapters.add(new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), i, tabTitles[i]));
        }
        mViewPager.setAdapter(mAdapters.get(mCurrentFragment));
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);

        setViewPagerListener();

        mCurrentFragment = 0;
        mViewPager.setAdapter(mAdapters.get(mCurrentFragment));

    }

    private void setViewPagerListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
