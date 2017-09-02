package com.playgroundideas.playgroundideas.plans;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgroundideas.playgroundideas.R;

public class PlansFragment extends Fragment {

    private PlanTabPagerAdapter planTabPagerAdapter;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_plans, container, false);

        // create the swipe views
        planTabPagerAdapter = new PlanTabPagerAdapter(getChildFragmentManager(), getContext());
        viewPager = rootView.findViewById(R.id.planPager);
        viewPager.setAdapter(planTabPagerAdapter);

        TabLayout tabLayout = rootView.findViewById(R.id.plan_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

}
