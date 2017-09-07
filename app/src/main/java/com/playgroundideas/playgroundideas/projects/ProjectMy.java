package com.playgroundideas.playgroundideas.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;

import com.joanzapata.iconify.widget.IconButton;
import com.playgroundideas.playgroundideas.MainActivity;
import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongNiu on 4/9/17.
 */

public class ProjectMy extends Fragment {

    private ListView mProjectSampleList;
    private ProjectsListAdapter mProjectListAdapter;
    private List<String> mProject;
    private static final int PROJECT_COUNTER = 10;
    private void initial_list() {
        mProject = new ArrayList<>();
        for(int i = 0; i< PROJECT_COUNTER; i++) {
            mProject.add("MyProject " + (i+1));
        }
    }
    private IconButton mCreateBtn;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.project_my, container, false);
        //use icon button
        mCreateBtn = (IconButton) rootView.findViewById(R.id.create_project);
        mCreateBtn.setOnClickListener(new IconButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProject();
            }
        });
        initial_list();
        mProjectListAdapter = new ProjectsListAdapter(getContext(),mProject);
        mProjectSampleList =  rootView.findViewById(R.id.project_my);
        mProjectSampleList.setAdapter(mProjectListAdapter);
        mProjectSampleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkProjectDetail();
            }
        });
        return rootView;
    }

    public void createProject() {
        Intent intent = new Intent();
        intent.setClass(getContext(), CreateProjectActivity.class);
        startActivity(intent);
    }

    public void checkProjectDetail() {
        Intent intent = new Intent();
        intent.setClass(getContext(), CreateProjectActivity.class);
        startActivity(intent);
    }

}
