package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.plans.PlanList;

/**
 * Created by Ferdinand on 2/09/2017.
 */

class ManualTabPagerAdapter extends FragmentPagerAdapter {

    private Resources resources;

    ManualTabPagerAdapter (FragmentManager fm, Context context) {
        super(fm);
        this.resources = context.getResources();
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0: return new ManualList();
            case 1: return new ManualList();
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
            case 0: return resources.getString(R.string.offline_manuals);
            case 1: return resources.getString(R.string.manuals);
            default: return resources.getString(R.string.manuals);
        }
    }
}
