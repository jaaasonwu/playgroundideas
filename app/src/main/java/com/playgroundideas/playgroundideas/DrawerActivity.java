package com.playgroundideas.playgroundideas;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import static com.playgroundideas.playgroundideas.MainActivity.NUM_BOTTOM_TABS;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<PagerAdapter> mAdapters;
    private int mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Playground Ideas");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        TextView settingButton = (TextView) menu.findItem(R.id.button_setting).getActionView();
        TextView createButton = (TextView) menu.findItem(R.id.button_create).getActionView();

        setFont(settingButton, iconFont, getString(R.string.setting_icon));
        setFont(createButton, iconFont, getString(R.string.create_icon));
        return true;
    }

    private void setFont(TextView textView, Typeface typeface, CharSequence string) {
        textView.setTypeface(typeface);
        textView.setText(string);
        textView.setPadding(24, 8, 24, 8);
        textView.setTextSize(32);
        textView.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.button_setting) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method defines the behavior when a different bottom navigation item is selected
     */
    @SuppressWarnings("StatementWithEmptyBody")
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
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            mViewPager.setAdapter(mAdapters.get(mCurrentFragment));
            return true;
        } else {
            return false;
        }
    }
}
