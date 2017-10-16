package com.playgroundideas.playgroundideas.designs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Design;

import java.util.ArrayList;

public class DesignBrowseList extends Fragment {

    private ArrayList<Design> favoriteList;
    private GridView myGrid;
    private SearchView searchView;
    private Spinner spinner;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteList = new ArrayList<Design>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_design_browse_list, container, false);
        // New varibles to commnicate with the facebook
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());
        myGrid = (GridView) view.findViewById(R.id.my_browse_grid);
        searchView = (SearchView) view.findViewById(R.id.search_browse);
        // Construct the adapter to fill data into view components
        final GridViewAdapterBrowse gridViewAdapterBrowse = new GridViewAdapterBrowse(getActivity(), favoriteList, callbackManager,shareDialog);
        myGrid.setAdapter(gridViewAdapterBrowse);
        // Initialize the searchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Filter filter = gridViewAdapterBrowse.getFilter();
                gridViewAdapterBrowse.previousQuery = query;
                filter.filter(query);
                return false;
            }
        });

        // Initialize the spinner
        spinner = (Spinner) view.findViewById(R.id.spinner_browse);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView option = (TextView) view;
                Toast.makeText(getContext(), option.getText() + " selected", Toast.LENGTH_SHORT).show();
                Filter filter = gridViewAdapterBrowse.getFilter();
                gridViewAdapterBrowse.catergory = option.getText().toString();
                filter.filter(gridViewAdapterBrowse.previousQuery);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    // Facebook sharing needs it
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
