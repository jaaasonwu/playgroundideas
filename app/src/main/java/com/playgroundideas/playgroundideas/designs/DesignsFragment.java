package com.playgroundideas.playgroundideas.designs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.FacebookSdk;
import com.playgroundideas.playgroundideas.R;



public class DesignsFragment extends Fragment {

    private DesignTabPagerAdapter designTabPagerAdapter;
    private ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_designs, container, false);
        // Construct the adapter used with the pager for switching between the favorite design page and the design browsing page
        // according to users' click.
        designTabPagerAdapter = new DesignTabPagerAdapter(getChildFragmentManager(), getContext());
        viewPager = rootView.findViewById(R.id.designPager);
        viewPager.setAdapter(designTabPagerAdapter);

        TabLayout tabLayout = rootView.findViewById(R.id.design_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    // The method would be used in the floating action button in reponse to a click for switching from the favorite design page to
    // the design browsing page.
    public void respond() {
        viewPager.setAdapter(designTabPagerAdapter);
        viewPager.setCurrentItem(1);
    }

}