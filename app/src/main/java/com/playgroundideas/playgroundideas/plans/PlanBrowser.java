package com.playgroundideas.playgroundideas.plans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgroundideas.playgroundideas.R;

/**
 * Created by Ferdinand on 27/08/2017.
 */

public class PlanBrowser extends PlanList {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.plan_list, container, false);
        return rootView;
    }
}
