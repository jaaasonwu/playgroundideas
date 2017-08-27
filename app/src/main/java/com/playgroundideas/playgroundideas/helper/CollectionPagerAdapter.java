package com.playgroundideas.playgroundideas.helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * Created by Ferdinand on 27/08/2017.
 */

public class CollectionPagerAdapter extends FragmentPagerAdapter {

    private List<? extends Class<? extends Fragment>> collection;

    public CollectionPagerAdapter(FragmentManager fragmentManager, List<? extends Class<? extends Fragment>> collection) {
        super(fragmentManager);
        this.collection = collection;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;

            try {
                fragment = collection.get(i).newInstance();
            } catch (InstantiationException ie) {
                fragment = new Fragment();
            } catch (IllegalAccessException eae) {
                fragment = new Fragment();
            }

        Bundle args = new Bundle();
        //set arguments here
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return this.collection.size();
    }

    @Override
    public String getPageTitle(int position) {
        return collection.get(position).getSimpleName();
    }

}
