package com.playgroundideas.playgroundideas.designs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    private GridView myGrid;
    private SearchView searchView;
    private Spinner spinner;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private GridViewAdapter gridViewAdapter;

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
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.onActionViewExpanded();
                searchView.setIconified(false);
                searchView.clearFocus();
            }
        });
        // Construct the adapter to fill data into view components
        gridViewAdapter = new GridViewAdapter(getActivity(),callbackManager,shareDialog,false);
        myGrid.setAdapter(gridViewAdapter);
        // Initialize the searchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Filter filter = gridViewAdapter.getFilter();
                gridViewAdapter.previousQuery = query;
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
                if (option==null) {
                    return;
                }
                Toast.makeText(getContext(), option.getText() + " selected", Toast.LENGTH_SHORT).show();
                Filter filter = gridViewAdapter.getFilter();
                gridViewAdapter.catergory = option.getText().toString();
                filter.filter(gridViewAdapter.previousQuery);
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

    /**
     * Update when the list is visible to the user
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && gridViewAdapter != null) {
            gridViewAdapter.notifyDataSetChanged();
        }
    }
}
