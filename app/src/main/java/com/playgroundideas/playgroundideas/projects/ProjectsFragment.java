package com.playgroundideas.playgroundideas.projects;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.List;


public class ProjectsFragment extends Fragment {



    private ProjectsTabPagerAdapter projectsTabPagerAdapter;
    private ViewPager viewPager;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(
                R.layout.fragment_projects, container, false);

        // create the swipe views
        projectsTabPagerAdapter = new ProjectsTabPagerAdapter(getChildFragmentManager(), getContext());
        viewPager = rootView.findViewById(R.id.projectPager);
        viewPager.setAdapter(projectsTabPagerAdapter);

        TabLayout tabLayout = rootView.findViewById(R.id.projects_tab);
        tabLayout.setupWithViewPager(viewPager);


        return rootView;
    }
}
