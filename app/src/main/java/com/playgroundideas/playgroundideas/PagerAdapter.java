package com.playgroundideas.playgroundideas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs = 2;
    private int mCurrentFragment;
    private Fragment mFragment;
    private CharSequence[] mTabTitles;

    static final int LEFT = 0;
    static final int RIGHT = 1;

    public PagerAdapter(FragmentManager fm, int numOfTabs, int currentFragment, CharSequence[] tabTitles) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
        this.mCurrentFragment = currentFragment;
        this.mTabTitles = tabTitles;
    }


    /**
     * Used to show the title of each tab
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        // Get different fragment according
        switch (mCurrentFragment) {
            case 0:
                mFragment = new DesignsFragment();
                break;
            case 1:
                mFragment = new PlansFragment();
                break;
            case 2:
                mFragment = new ManualsFragment();
                break;
            case 3:
                mFragment = new ProjectsFragment();
                break;
        }
        Bundle args = new Bundle();
        // Get different page for each fragment
        switch (position) {
            case 0:
                args.putInt("position", LEFT);
                mFragment.setArguments(args);
                return mFragment;
            case 1:
                args.putInt("position", RIGHT);
                mFragment.setArguments(args);
                return mFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
