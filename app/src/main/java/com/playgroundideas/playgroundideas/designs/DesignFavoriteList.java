package com.playgroundideas.playgroundideas.designs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.User;
import com.playgroundideas.playgroundideas.viewmodel.DesignListViewModel;
import com.playgroundideas.playgroundideas.viewmodel.UserViewModel;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class DesignFavoriteList extends DaggerFragment {

    private FloatingActionButton designsAddFab;
    private GridView gridView;
    private DesignListViewModel viewModel;
    private UserViewModel userViewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private DesignGridViewAdapter gridViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.designsAddFab = getActivity().findViewById(R.id.add_designs_fab);
        designsAddFab.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_add)
                .colorRes(R.color.white).actionBarSize());
        designsAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move to tab with all designs so the user can select a favourite one
                ((DesignsFragment) getParentFragment()).setFocusToAll();
            }
        });

        // this integrates the view model
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DesignListViewModel.class);
        userViewModel.init(userViewModel.getCurrentUserId());
        userViewModel.getLiveUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                viewModel.setTo(true, user);
                viewModel.getDesignList().observe(getParentFragment(), new Observer<List<Pair<Design, Boolean>>>() {
                    @Override
                    public void onChanged(@Nullable List<Pair<Design, Boolean>> designs) {
                        gridViewAdapter.updateDesigns(designs);
                    }
                });
                gridViewAdapter = new DesignGridViewAdapter(getContext(), new LinkedList<Pair<Design, Boolean>>(), viewModel, user);
            }
        });

        gridViewAdapter = new DesignGridViewAdapter(getContext(), new LinkedList<Pair<Design, Boolean>>(), viewModel, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_design_favorite_list, container, false);
        gridView = rootView.findViewById(R.id.favorite_designs_grid);
        gridView.setAdapter(gridViewAdapter);
        return rootView;
    }
}
