package com.playgroundideas.playgroundideas.designs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.viewmodel.DesignListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class DesignFavoriteList extends DaggerFragment {

    private DesignsFragment designsFragment;
    private FloatingActionButton designsAddFab;
    private GridView myFavoriteGrid;
    private SearchView searchView;
    private Spinner spinner;
    private DesignListViewModel viewModel;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private GridViewAdapter gridViewAdapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_design_favorite_list, container, false);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());
        myFavoriteGrid = view.findViewById(R.id.my_favorite_grid);
        searchView = (SearchView) view.findViewById(R.id.search_favorite);
        // Construct the adapter to fill data into view components
        gridViewAdapter = new GridViewAdapter(getActivity(),callbackManager,shareDialog,true);
        myFavoriteGrid.setAdapter(gridViewAdapter);
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
        spinner = (Spinner) view.findViewById(R.id.spinner_favorite);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView option = (TextView) view;
                if (option == null) {
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


    // Initialize a floating action button for switching from the favorite design page to the design browsing page
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        //this integrates the design view model
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DesignListViewModel.class);
        viewModel.init(true);
        viewModel.getDesignList().observe(this, new Observer<List<Design>>() {
            @Override
            public void onChanged(@Nullable List<Design> designs) {
               // gridViewAdapter.designItemsChanged(designs);

            }
        });

    }

    //Facebook sharing needs it
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
