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
    protected ArrayList<String> groupHeader;
    protected HashMap<String, Boolean> downloadStatus;
    protected HashMap<String, List<String>> itemHeader;

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
                args.putStringArrayList("groupHeader", groupHeader);
                args.putSerializable("downloadStatus", downloadStatus);
                args.putSerializable("itemHeader", itemHeader);
                ManualExpandableList manualExpandableList = new ManualExpandableList();
                manualExpandableList.setArguments(args);
                return manualExpandableList;
            case 1:
                return null;
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
        groupHeader = new ArrayList<>();
        itemHeader = new HashMap<>();
        downloadStatus = new HashMap<>();
        List<String> starter = new ArrayList<>();

        groupHeader.add("Playground Starter");
        groupHeader.add("Builder's Handbook");
        groupHeader.add("Safety Handbook");
        groupHeader.add("Loose Parts Manual");
        groupHeader.add("Inclusive Design Manual");
        groupHeader.add("Cut & Paste");
        groupHeader.add("Teacher Training");
        groupHeader.add("Case for Play");

        List<String> dumbChildList = new ArrayList<>();
        dumbChildList.add("Welcome");
        dumbChildList.add("Let's Build A Playground");
        dumbChildList.add("Site Plan");
        dumbChildList.add("Elements");
        dumbChildList.add("Join Us!");

        for (String s : groupHeader) {
            itemHeader.put(s, dumbChildList);
            downloadStatus.put(s, Boolean.FALSE);
        }
    }
}
