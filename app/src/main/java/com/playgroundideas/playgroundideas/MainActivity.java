package com.playgroundideas.playgroundideas;

import android.content.res.ColorStateList;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private LayoutInflater mInflater;
    private ViewPager mViewPager;
    private TabLayout.Tab mLeftTab;
    private TabLayout.Tab mRightTab;
    private ArrayList<PagerAdapter> mAdapters;
    public int mCurrentFragment;

    static final int NUM_BOTTOM_TABS = 4;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        /**
         * This method defines the behavior when a different bottom navigation item is selected
         */
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            boolean success = false;
            switch (item.getItemId()) {
                case R.id.navigation_designs:
                    mLeftTab.setText(R.string.fav_designs);
                    mRightTab.setText(R.string.brs_designs);
                    mCurrentFragment = 0;
                    success = true;
                    break;
                case R.id.navigation_plans:
                    mLeftTab.setText(R.string.my_plans);
                    mRightTab.setText(R.string.brs_plans);
                    mCurrentFragment = 1;
                    success = true;
                    break;
                case R.id.navigation_manuals:
                    mLeftTab.setText(R.string.manuals);
                    mRightTab.setText(R.string.offline_manuals);
                    mCurrentFragment = 2;
                    success = true;
                    break;
                case R.id.navigation_projects:
                    mLeftTab.setText(R.string.my_projects);
                    mRightTab.setText(R.string.brs_projects);
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

        //Define the top tabs
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.fav_designs));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.brs_designs));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Get the created tabs
        mLeftTab = mTabLayout.getTabAt(0);
        mRightTab = mTabLayout.getTabAt(1);

        mViewPager = (ViewPager) findViewById(R.id.main_pager);
        mCurrentFragment = 0;

        mAdapters = new ArrayList<>(4);
        for (int i = 0; i < NUM_BOTTOM_TABS; i++) {
            mAdapters.add(new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), i));
        }
        mViewPager.setAdapter(mAdapters.get(mCurrentFragment));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setCurrentItem(0);

        setTabListener();

    }

    private void setTabListener() {
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
