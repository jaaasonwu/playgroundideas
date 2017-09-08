package com.playgroundideas.playgroundideas.projects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by TongNiu on 4/9/17.
 */

public class ProjectBrowser extends Fragment {


    private ListView mProjectSampleList;
    private ProjectsListAdapter mProjectListAdapter;
    private List<ProjectItem> mProject;
    private static final int PROJECT_COUNTER = 10;
    private Bitmap mProjectImage;
    private Calendar mCalendar = Calendar.getInstance();
    private void initial_list() {
        mProjectImage = BitmapFactory.decodeResource(getResources(),R.drawable.image_project);
        mProject = new ArrayList<>();
        ProjectItem newProject;
        for(int i = 0; i< PROJECT_COUNTER; i++) {
            newProject = new ProjectItem("Project" + " " + i,mProjectImage);
            mProject.add(newProject);
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
