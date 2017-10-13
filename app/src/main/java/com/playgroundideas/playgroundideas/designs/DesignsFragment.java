package com.playgroundideas.playgroundideas.designs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgroundideas.playgroundideas.R;



public class DesignsFragment extends Fragment {

    private DesignTabPagerAdapter designTabPagerAdapter;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_designs, container, false);

        // create the swipe views
        designTabPagerAdapter = new DesignTabPagerAdapter(getChildFragmentManager(), getContext());
        viewPager = rootView.findViewById(R.id.designPager);
        viewPager.setAdapter(designTabPagerAdapter);

        TabLayout tabLayout = rootView.findViewById(R.id.design_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    public void setFocusToAll() {
        this.viewPager.setCurrentItem(1);
    }
}