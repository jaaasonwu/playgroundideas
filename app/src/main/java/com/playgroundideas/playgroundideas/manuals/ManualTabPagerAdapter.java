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
            case 0: return resources.getString(R.string.offline_manuals);
            case 1: return resources.getString(R.string.manuals);
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

        List<List<String>> childLists = new ArrayList<>();

        List<String> childList = new ArrayList<>();
        childList.add("Welcome");
        childList.add("Let's Build A Playground");
        childList.add("Site Plan");
        childList.add("Elements");
        childList.add("Join Us!");
        childLists.add(childList);

        childList = new ArrayList<>();
        childList.add("Introduction");
        childList.add("Step 1: Listen");
        childList.add("Step 2: Plan");
        childList.add("Step 3: Design");
        childList.add("Step 4: Build");
        childList.add("Step 5: Maintain");
        childList.add("Appendices");
        childLists.add(childList);

        childList = new ArrayList<>();
        childList.add("Managing");
        childList.add("Hazards");
        childList.add("Heights + Fall Zone");
        childLists.add(childList);

        childList = new ArrayList<>();
        childList.add("Welcome");
        childList.add("Benefits Of Loose Parts");
        childList.add("Step 1: Assess Your Environment");
        childList.add("Step 2: Gather Loose Parts Materials");
        childList.add("Step 3: Storage And Maintenance");
        childList.add("Step 4: Train Staff");
        childList.add("Going Deeper");
        childList.add("Thank You");
        childList.add("Further Reading & Resources");
        childLists.add(childList);

        childList = new ArrayList<>();
        childList.add("Intro");
        childList.add("Listen");
        childList.add("Tips And Strategies");
        childList.add("Design");
        childLists.add(childList);

        childList = new ArrayList<>();
        childList.add("Cut & Paste Playground Designer");
        childLists.add(childList);

        childList = new ArrayList<>();
        childList.add("Welcome");
        childList.add("The Transforming Power Of Play");
        childList.add("Why Do Children Play");
        childList.add("What Does Play Look Like");
        childList.add("Obstacles To Play");
        childList.add("Rote Based Learning Vs. Play Based Learning");
        childList.add("Be A Play Advocate");
        childList.add("Keep Learning");
        childList.add("References");
        childLists.add(childList);


        childList = new ArrayList<>();
        childList.add("Executive Summary");
        childList.add("Introduction");
        childList.add("The Issue");
        childList.add("Potential Of Play Interventions");
        childList.add("Call To Action");
        childList.add("References");
        childLists.add(childList);


        for (int i = 0; i < mGroupHeader.size(); i++) {
            mItemHeader.put(mGroupHeader.get(i), childLists.get(i));
            mDownloadStatus.put(mGroupHeader.get(i), Boolean.FALSE);
        }
    }
}
