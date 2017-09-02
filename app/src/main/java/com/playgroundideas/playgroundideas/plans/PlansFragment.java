package com.playgroundideas.playgroundideas.plans;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.helper.SectionPagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class PlansFragment extends Fragment {

    SectionPagerAdapter sectionPagerAdapter;
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
        List<Class<? extends Fragment>> tabs = new LinkedList<Class<? extends Fragment>>();
        tabs.add(PlanList.class);
        tabs.add(PlanBrowser.class);

        sectionPagerAdapter = new SectionPagerAdapter(getChildFragmentManager(), tabs);
        viewPager = rootView.findViewById(R.id.planPager);
        viewPager.setAdapter(sectionPagerAdapter);

        TabLayout tabLayout = rootView.findViewById(R.id.plan_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

}
