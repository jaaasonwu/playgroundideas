package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
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
        fillListData();
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0:
                Bundle args = new Bundle();
                args.putStringArrayList("groupHeader", mGroupHeader);
                args.putSerializable("downloadStatus", mDownloadStatus);
                args.putSerializable("itemHeader", mItemHeader);
                ManualExpandableList manualExpandableList = new ManualExpandableList();
                manualExpandableList.setArguments(args);
                return manualExpandableList;
            case 1:
                args = new Bundle();
                args.putStringArrayList("groupHeader", mGroupHeader);
                args.putSerializable("downloadStatus", mDownloadStatus);
                ManualsOfflineList manualsOfflineList = new ManualsOfflineList();
                manualsOfflineList.setArguments(args);
                return manualsOfflineList;
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
        switch (position) {
            case 0: return resources.getString(R.string.manuals);
            case 1: return resources.getString(R.string.offline_manuals);
            default: return resources.getString(R.string.manuals);
        }
    }

    private void fillListData() {
        mGroupHeader = new ArrayList<>();
        mItemHeader = new HashMap<>();
        mDownloadStatus = new HashMap<>();
        List<String> starter = new ArrayList<>();

        mGroupHeader.add("Playground Starter");
        mGroupHeader.add("Builder's Handbook");
        mGroupHeader.add("Safety Handbook");
        mGroupHeader.add("Loose Parts Manual");
        mGroupHeader.add("Inclusive Design Manual");
        mGroupHeader.add("Cut & Paste");
        mGroupHeader.add("Teacher Training");
        mGroupHeader.add("Case for Play");

        List<String> dumbChildList = new ArrayList<>();
        dumbChildList.add("Welcome");
        dumbChildList.add("Let's Build A Playground");
        dumbChildList.add("Site Plan");
        dumbChildList.add("Elements");
        dumbChildList.add("Join Us!");

        for (String s : mGroupHeader) {
            mItemHeader.put(s, dumbChildList);
            mDownloadStatus.put(s, Boolean.FALSE);
        }
    }
}
