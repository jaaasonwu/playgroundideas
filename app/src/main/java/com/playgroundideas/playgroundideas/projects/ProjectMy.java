package com.playgroundideas.playgroundideas.projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.widget.IconButton;
import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ProjectMy extends Fragment {

    private ListView mProjectSampleList;
    private ProjectsListAdapter mProjectListAdapter;
    private List<ProjectItem> mProject;
    private static final int PROJECT_COUNTER = 10;
    private FloatingActionButton mCreateBtn;
    private SearchView mSearchView;
    private Spinner mSorts = null;
    public static final String[] mSortSelections = {"None","Country"};
    public static final String defaultText = "Filter By";


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.project_my, container, false);
        //use icon button

        mCreateBtn = (FloatingActionButton) rootView.findViewById(R.id.create_project);
        mCreateBtn.setOnClickListener(new IconButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProject();
            }
        });
        initial_list();
        mSorts = (Spinner) rootView.findViewById(R.id.spinner_sort);
       mSorts.setAdapter(new SortSpinnerAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,mSortSelections,defaultText));
        mProjectListAdapter = new ProjectsListAdapter(getContext(),mProject);
        mProjectSampleList =  rootView.findViewById(R.id.project_my);
        mProjectSampleList.setAdapter(mProjectListAdapter);
        mProjectSampleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkProjectDetail();
            }
        });
        mCreateBtn.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_add)
                .colorRes(R.color.white).actionBarSize());
        mSearchView = (SearchView)rootView.findViewById(R.id.projectSearch);
        setupSearchView();
        return rootView;
    }

    public void createProject() {
        Intent intent = new Intent();
        intent.setClass(getContext(), CreateProjectActivity.class);
        startActivity(intent);
    }

    public void checkProjectDetail() {
        Intent intent = new Intent();
        intent.setClass(getContext(), DetailProjectActivity.class);
        startActivity(intent);
    }

    private void initial_list() {
        Calendar mCalendar = Calendar.getInstance();
        mProject = new ArrayList<>();
        Date sampleDate = mCalendar.getTime();
        String sampleTitle = "My Project";
        String sampleEmailAddress = "playpus@gmail.com";
        String sampleCountry = "Australia";
        String sampleCurrency = "AUD";
        String sampleDescription = "It is my first project";
        String sampleImageUrl = "https://playgroundideas.org/wp-content/uploads/2017/02/IMGP0204-1024x768.jpg";
        ProjectItem newProject;
        for(int i = 0; i< PROJECT_COUNTER; i++) {
            newProject = new ProjectItem(sampleTitle+ " " + i,sampleDate,sampleDate,sampleEmailAddress
                    ,sampleCountry,sampleCurrency,sampleDescription,sampleImageUrl);
            mProject.add(newProject);
        }
    }

    private void setupSearchView() {
        mSearchView.setQueryHint("search title");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)) {
                    mProjectSampleList.clearTextFilter();
                } else {
                    mProjectListAdapter.getFilter().filter(s);
                }
                return true;
            }
        });
    }

    public class SortSpinnerAdapter extends ArrayAdapter<String> {
        Context mContext;
        String[] objects;
        String firstElement;
        boolean isFirstTime;

        public SortSpinnerAdapter(Context mContext,int textViewResourceId, String[] objects, String defaultText) {
            super(mContext,textViewResourceId,objects);
            this.mContext = mContext;
            this.objects = objects;
            this.isFirstTime = true;
            setDefaultText(defaultText);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            notifyDataSetChanged();
            return  getCustomView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if(isFirstTime) {
                objects[0] = firstElement;
                isFirstTime = false;
            }
            return  getCustomView(position, convertView, parent);
        }

        public void setDefaultText(String defaultText) {
            this.firstElement = objects[0];
            objects[0] = defaultText;
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
            TextView label = (TextView) row.findViewById(android.R.id.text1);
            label.setText(objects[position]);
            return row;
        }
    }


}
