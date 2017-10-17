package com.playgroundideas.playgroundideas.designs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.User;
import com.playgroundideas.playgroundideas.viewmodel.DesignListViewModel;
import com.playgroundideas.playgroundideas.viewmodel.UserViewModel;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class DesignBrowseList extends DaggerFragment {

    private GridView gridView;
    private DesignListViewModel designListViewModel;
    private UserViewModel userViewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private DesignGridViewAdapter gridViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // this integrates the view model
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        designListViewModel = ViewModelProviders.of(this, viewModelFactory).get(DesignListViewModel.class);
        userViewModel.init(userViewModel.getCurrentUserId());
        userViewModel.getLiveUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                designListViewModel.setTo(true, user);
                designListViewModel.getDesignList().observe(getParentFragment(), new Observer<List<Pair<Design, Boolean>>>() {
                    @Override
                    public void onChanged(@Nullable List<Pair<Design, Boolean>> designs) {
                        gridViewAdapter.updateDesigns(designs);
                    }
                });
                gridViewAdapter = new DesignGridViewAdapter(getContext(), new LinkedList<Pair<Design, Boolean>>(), designListViewModel, user);
            }
        });

        gridViewAdapter = new DesignGridViewAdapter(getContext(), new LinkedList<Pair<Design, Boolean>>(), designListViewModel, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_design_browse_list, container, false);
        gridView = rootView.findViewById(R.id.designs_grid);
        gridView.setAdapter(gridViewAdapter);
        return rootView;
    }
}
