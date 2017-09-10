package com.playgroundideas.playgroundideas.projects;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;


public class ProjectBrowser extends Fragment {


    private ListView mProjectSampleList;
    private ProjectsListAdapter mProjectListAdapter;
    private List<ProjectItem> mProject;
    private static final int PROJECT_COUNTER = 10;
    private Calendar mCalendar = Calendar.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.project_browser, container, false);

        initial_list();
        mProjectListAdapter = new ProjectsListAdapter(getContext(),mProject);
        mProjectSampleList =  rootView.findViewById(R.id.project_list);
        mProjectSampleList.setAdapter(mProjectListAdapter);
        mProjectSampleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkProjectDetail();
            }
        });
        return rootView;
    }

    private void initial_list() {
        mProject = new ArrayList<>();
        Date sampleDate = mCalendar.getTime();
        String sampleEmailAddress = "playpus@gmail.com";
        String sampleCountry = "Australia";
        String sampleCurrency = "AUD";
        String sampleDescription = "It is my first project";
        String sampleTitle = "My Project";
        String sampleImageUrl = "https://playgroundideas.org/wp-content/uploads/2017/02/IMGP0204-1024x768.jpg";
        ProjectItem newProject;
        for(int i = 0; i< PROJECT_COUNTER; i++) {
            newProject = new ProjectItem(sampleTitle+ " " + i,sampleDate,sampleDate,sampleEmailAddress
                    ,sampleCountry,sampleCurrency,sampleDescription,sampleImageUrl);
            mProject.add(newProject);
        }
    }

    public void checkProjectDetail() {
        Intent intent = new Intent();
        intent.setClass(getContext(), DetailProjectActivity.class);
        startActivity(intent);
    }
}
