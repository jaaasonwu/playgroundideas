package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.plans.PlanBrowser;
import com.playgroundideas.playgroundideas.plans.PlanList;

// The Pager Adpater is defined for switching between the favorite design page and the design browsing page.
class DesignTabPagerAdapter extends FragmentPagerAdapter {

    private Resources resources;

    DesignTabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.resources = context.getResources();
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0: return new DesignFavoriteList();
            case 1: return new DesignBrowseList();
            default: return new DesignFavoriteList();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public String getPageTitle(int position) {
        switch (position) {
            case 0: return resources.getString(R.string.fav_designs);
            case 1: return resources.getString(R.string.brs_designs);
            default: return resources.getString(R.string.fav_designs);
        }
    }
}
