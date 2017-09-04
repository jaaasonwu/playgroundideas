package com.playgroundideas.playgroundideas.plans;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.playgroundideas.playgroundideas.R;

/**
 * Created by Ferdinand on 2/09/2017.
 */

class PlanTabPagerAdapter extends FragmentPagerAdapter {

    private Resources resources;

    PlanTabPagerAdapter (FragmentManager fm, Context context) {
        super(fm);
        this.resources = context.getResources();
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0: return new PlanList();
            case 1: return new PlanBrowser();
            default: return new PlanList();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public String getPageTitle(int position) {
        switch (position) {
            case 0: return resources.getString(R.string.my_projects);
            case 1: return resources.getString(R.string.brs_projects);
            default: return resources.getString(R.string.my_projects);
        }
    }
}
