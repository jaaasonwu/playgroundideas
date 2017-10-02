package com.playgroundideas.playgroundideas.designs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;

import com.daimajia.swipe.util.Attributes;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.viewmodel.DesignListViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class DesignFavoriteList extends DaggerFragment {

    private DesignsFragment designsFragment;
    private FloatingActionButton designsAddFab;
    private GridView myFavoriteGrid;
    private SearchView searchView;
    private DesignListViewModel viewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_design_favorite_list, container, false);
        myFavoriteGrid = view.findViewById(R.id.my_favorite_grid);
        searchView = (SearchView) view.findViewById(R.id.searchView1);
        final GridViewAdapterFavorite gridViewAdapterFavorite = new GridViewAdapterFavorite(getActivity());
        myFavoriteGrid.setAdapter(gridViewAdapterFavorite);
        gridViewAdapterFavorite.setMode(Attributes.Mode.Single);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                gridViewAdapterFavorite.getFilter().filter(query);
                return false;
            }
        });
        return view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_design_favorite_list, container, false);
        myFavoriteGrid = view.findViewById(R.id.my_favorite_grid);
        myFavoriteGrid.setAdapter(new GridViewAdapterFavorite(getActivity()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.designsAddFab = getActivity().findViewById(R.id.add_designs_fab);
        designsAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                designsFragment = (DesignsFragment) getParentFragment();
                designsFragment.respond();
            }
        });
        designsAddFab.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_add)
                .colorRes(R.color.white).actionBarSize());



        // this integrates the design view model
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DesignListViewModel.class);
        viewModel.init(true);
        viewModel.getDesignList().observe(this, new Observer<List<Design>>() {
            @Override
            public void onChanged(@Nullable List<Design> designs) {
                // TODO Update UI
            }
        });

    }
}
