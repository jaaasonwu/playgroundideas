package com.playgroundideas.playgroundideas.projects;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private SearchView mSearchView;
    private Button mFilter;
    private PopupWindow mPopWindow;
    private Spinner mFilterByCountry = null;
    public static final String[] mSortSelections = {"-","Australia","American","South Africa","China","All"};
    public static final String defaultText = "Location";


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.project_browser, container, false);

        initial_list();

        mFilter = (Button) rootView.findViewById(R.id.button_Filter);
        mProjectListAdapter = new ProjectsListAdapter(getContext(),mProject);
        mProjectSampleList =  rootView.findViewById(R.id.project_list);
        mProjectSampleList.setAdapter(mProjectListAdapter);
        mProjectSampleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkProjectDetail(mProjectListAdapter.getItem(i));
            }
        });

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

    private void initial_list() {
        Calendar mCalendar = Calendar.getInstance();
        mProject = new ArrayList<>();
        Date sampleDate = mCalendar.getTime();
        String[] sampleTitle = {"Go to play"
                                ,"Gawad Kalinga Village Playground"
                                ,"Children on the Edge"
                                ,"Rolling Dreams"
                                ,"Maendeleo Playscape & Farm"
                                ,"AMOR ACTIVO COPAN"
                                ,"Pact Playground"
                                ,"Morocco Interactive Playspace"
                                ,"St. Monica’s Preschool"
                                ,"Preschool Playground"};
        String sampleEmailAddress = "playpus@gmail.com";
        String sampleCountry;
        String sampleCurrency = "AUD";
        String sampleDescription = "This recycled playground was built at a Gawad Kalinga Village in Davao, Philippines in November 2011.The playground was built in a housing development of about 100 small, low cost homes. An unexpected donation of 20 tractor tires, enabled us create interesting cubby features as a part of the design. Design by Playground Ideas, rendering by Howard Lorenzo, photography by Macky Madalangconsuegra.";
        String[] sampleImageUrl = {"https://playgroundideas.org/wp-content/uploads/2017/02/IMGP0204-1024x768.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_0871-1024x768.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_0782-1024x572.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMGP0184-1024x768.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2269-1024x683.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2269-1024x683.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2370-1024x683.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_8878-1024x768.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2345-1024x683.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_8831-1024x768.jpg"
                ,"https://playgroundideas.org/wp-content/uploads/2017/02/IMG_2407-1024x683.jpg"};
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
                    ,sampleCountry,sampleCurrency,sampleDescription,sampleImageUrl[i],i*100+1042,i*1000+4123,i);
            mProject.add(newProject);
        }
    }

    public void checkProjectDetail(ProjectItem pro) {
        Intent intent = new Intent(getContext(),DetailProjectActivity.class);
        intent.setClass(getContext(), DetailProjectActivity.class);
        intent.putExtra("project_data",pro);
        startActivity(intent);
    }

    //setup search function
    private void setupSearchView() {
        mSearchView.setQueryHint("search title");
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.onActionViewExpanded();
                mSearchView.setIconified(false);
                mSearchView.clearFocus();
            }
        });
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


    //filter project by item
    public void filterProjectBy() {
        View conternView = LayoutInflater.from(getContext()).inflate(R.layout.project_filter_popupview,null);
        mPopWindow = new PopupWindow(conternView);
        mPopWindow.setContentView(conternView);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mFilterByCountry = (Spinner) conternView.findViewById(R.id.spinner_filter_country);
        mFilterByCountry.setAdapter(new ProjectBrowser.SortSpinnerAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,mSortSelections,defaultText));
        mPopWindow.showAsDropDown(mFilter);
        filterProject();
    }


    //set the default title of spinner
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


    //filter project by country
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

