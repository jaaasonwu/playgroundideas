package com.playgroundideas.playgroundideas.projects;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button mFilter;
    private PopupWindow mPopWindow;
    private Spinner mFilterByCountry = null;
    public static final String[] mSortSelections = {"-","Australia","American","Rwanda","China","All"};
    public static final String defaultText = "Location";


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(
                R.layout.project_my, container, false);
        //use icon button

        mCreateBtn = (FloatingActionButton) rootView.findViewById(R.id.create_project);
        mCreateBtn.setOnClickListener(new IconButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProject();
            }
        });
        mFilter = (Button) rootView.findViewById(R.id.button_Filter);
        initial_list();
        mProjectListAdapter = new ProjectsListAdapter(getContext(),mProject);
        mProjectSampleList =  rootView.findViewById(R.id.project_my);
        mProjectSampleList.setAdapter(mProjectListAdapter);
        mProjectSampleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkProjectDetail(mProjectListAdapter.getItem(i));
            }
        });
        mCreateBtn.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_add)
                .colorRes(R.color.white).actionBarSize());
        mSearchView = (SearchView)rootView.findViewById(R.id.projectSearch);
        setupSearchView();
        mFilter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                    filterProjectBy();
            }
        });
        return rootView;
    }

    public void createProject() {
        Intent intent = new Intent();
        intent.setClass(getContext(), CreateProjectActivity.class);
        startActivity(intent);
    }

    public void checkProjectDetail(ProjectItem pro) {
        Intent intent = new Intent(getContext(),DetailProjectActivity_my.class);
        intent.setClass(getContext(), DetailProjectActivity_my.class);
        intent.putExtra("project_data",pro);
        startActivity(intent);
    }

    private void initial_list() {
        Calendar mCalendar = Calendar.getInstance();
        mProject = new ArrayList<>();
        Date sampleDate = mCalendar.getTime();
        String[] sampleTitle = {"Go to play"
                ,"Gawad Kalinga Village Playground"
                ,"Children on the Edge"
                ,"Maendeleo Playscape & Farm"
                ,"AMOR ACTIVO COPAN"
                ,"Morocco Interactive Playspace"
                ,"Pact Playground"
                ,"St. Monica’s Preschool"
                ,"Rolling Dreams"
                ,"Preschool Playground"};
        String sampleEmailAddress = "platypustestplatyground@gmail.com";
        String sampleCountry;
        String sampleCurrency = "AUD";
        String sampleDescription = "This playground project was a partnership between  University of Canberra, Playground Ideas, Pacific Adventist University and Koiari Park Adventist Primary School. The playground was built to be a demonstration playground and research site, with the long-term goal to promote playgrounds built of locally sourced materials as safe and stimulating play and learning environments for children and also to serve as community hubs.";
        String[] sampleImageUrl = {"https://playgroundideas.org/wp-content/uploads/2017/02/IMGP0204-1024x768.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_0782-1024x572.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_0871-1024x768.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMGP0184-1024x768.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2269-1024x683.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2269-1024x683.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2345-1024x683.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2370-1024x683.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2407-1024x683.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_8878-1024x768.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_8831-1024x768.jpg"};
        ProjectItem newProject;
        for(int i = 0; i< PROJECT_COUNTER ; i++) {
            if(i < 2) {
                sampleCountry = "China";
            } else if(i < 5){
                sampleCountry = "Australia";
            } else if(i < 8) {
                sampleCountry = "Rwanda";
            } else {
                sampleCountry = "American";
            }
            newProject = new ProjectItem(sampleTitle[i],sampleDate,sampleDate,sampleEmailAddress
                    ,sampleCountry,sampleCurrency,sampleDescription,sampleImageUrl[i],i*100+1231,i*1000+1321,i);
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
                mProjectListAdapter.getFilter().filter(s);
                mProjectListAdapter.setPreviousQuery(s);
                return true;
            }
        });
    }


    public void filterProjectBy() {
        View conternView = LayoutInflater.from(getContext()).inflate(R.layout.project_filter_popupview,null);
        mPopWindow = new PopupWindow(conternView);
        mPopWindow.setContentView(conternView);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), android.R.color.transparent)));
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mFilterByCountry = (Spinner) conternView.findViewById(R.id.spinner_filter_country);
        final SortSpinnerAdapter mCountryAdapter = new SortSpinnerAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,mSortSelections,defaultText);
        mFilterByCountry.setAdapter(mCountryAdapter);
        mPopWindow.showAsDropDown(mFilter);
        filterProject();
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mCountryAdapter.setFirstTime(true);
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

        public void setFirstTime(Boolean firstTime) {
            isFirstTime = firstTime;
        }
    }


    public void filterProject() {
        mFilterByCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int a = 1,b = 0;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(b<a) {
                    b++;
                }
                else {
                    mProjectListAdapter.setFilterByCountry(mFilterByCountry.getSelectedItem().toString());
                    mProjectListAdapter.getFilter().filter(mProjectListAdapter.getPreviousQuery());
                    if(mFilterByCountry.getSelectedItem().toString().equalsIgnoreCase("all")) {
                       mFilter.setText("Filter By");
                    } else {
                        mFilter.setText(mFilterByCountry.getSelectedItem().toString());
                    }
                    Toast.makeText(getContext(), "Project in " + mFilterByCountry.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                    mPopWindow.dismiss();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
