package com.playgroundideas.playgroundideas.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongNiu on 4/9/17.
 */

public class ProjectBrowser extends Fragment {

    private ListView mProjectSampleList;
    private ProjectsListAdapter mProjectListAdapter;
    private List<String> mProject;
    private static final int PROJECT_COUNTER = 10;
    private void initial_list() {
        mProject = new ArrayList<>();
        for(int i = 0; i< PROJECT_COUNTER; i++) {
            mProject.add("Project " + (i+1));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.project_browser, container, false);

        initial_list();
        mProjectListAdapter = new ProjectsListAdapter(getContext(),mProject);
        mProjectSampleList =  rootView.findViewById(R.id.project_list);
        mProjectSampleList.setAdapter(mProjectListAdapter);
        return rootView;
    }
}
