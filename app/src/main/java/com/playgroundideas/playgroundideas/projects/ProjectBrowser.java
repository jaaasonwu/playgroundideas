package com.playgroundideas.playgroundideas.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgroundideas.playgroundideas.R;

/**
 * Created by TongNiu on 4/9/17.
 */

public class ProjectBrowser extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.project_browser, container, false);
        return rootView;
    }
}
