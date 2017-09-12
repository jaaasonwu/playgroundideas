package com.playgroundideas.playgroundideas.manuals;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgroundideas.playgroundideas.R;

public class ManualsFragment extends Fragment {

    ManualTabPagerAdapter manualTabPagerAdapter;
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manuals, container, false);

        // create the swipe views
        manualTabPagerAdapter = new ManualTabPagerAdapter(getChildFragmentManager(), getContext());
        viewPager = rootView.findViewById(R.id.manualPager);
        viewPager.setAdapter(manualTabPagerAdapter);

        TabLayout tabLayout = rootView.findViewById(R.id.manual_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }
}