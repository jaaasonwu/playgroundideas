package com.playgroundideas.playgroundideas.designs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.playgroundideas.playgroundideas.MainActivity;
import com.playgroundideas.playgroundideas.R;


public class DesignsFragment extends Fragment {
    private GridView myGrid;
    private int position;
    private Button button;

    private DesignTabPagerAdapter designTabPagerAdapter;
    private ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_designs, container, false);
        designTabPagerAdapter = new DesignTabPagerAdapter(getChildFragmentManager(), getContext());
        viewPager = rootView.findViewById(R.id.designPager);
        viewPager.setAdapter(designTabPagerAdapter);

        TabLayout tabLayout = rootView.findViewById(R.id.design_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    public void respond() {
        viewPager.setAdapter(designTabPagerAdapter);
        viewPager.setCurrentItem(1);
    }
}