package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ManualTabPagerAdapter extends FragmentPagerAdapter {

    private Resources resources;
    private ArrayList<String> mGroupHeader;
    private HashMap<String, Boolean> mDownloadStatus;
    private HashMap<String, List<String>> mItemHeader;

    ManualTabPagerAdapter (FragmentManager fm, Context context) {
        super(fm);
        this.resources = context.getResources();
    }

    @Override
    public Fragment getItem(int i) {
        // Create fragments for each tab
        switch(i) {
            case 0:
                return new ManualExpandableList();
            case 1:
                return new ManualsOfflineList();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public String getPageTitle(int position) {
        // Set the title for tabs
        switch (position) {
            case 0: return resources.getString(R.string.manuals);
            case 1: return resources.getString(R.string.offline_manuals);
            default: return resources.getString(R.string.manuals);
        }
    }
}
