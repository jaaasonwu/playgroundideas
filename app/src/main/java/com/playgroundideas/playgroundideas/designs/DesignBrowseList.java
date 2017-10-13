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
import com.playgroundideas.playgroundideas.viewmodel.DesignListViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class DesignBrowseList extends DaggerFragment {

    private GridView gridView;
    private DesignListViewModel viewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private DesignGridViewAdapter gridViewAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // this integrates the design view model
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DesignListViewModel.class);
        viewModel.init(false);
        viewModel.getDesignList().observe(this, new Observer<List<Pair<Design, Boolean>>>() {
            @Override
            public void onChanged(@Nullable List<Pair<Design, Boolean>> designs) {
                gridViewAdapter.updateDesigns(designs);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_design_browse_list, container, false);
        gridView = rootView.findViewById(R.id.designs_grid);
        gridViewAdapter = new DesignGridViewAdapter(getContext(), viewModel.getDesignList().getValue(), viewModel);
        gridView.setAdapter(gridViewAdapter);
        return rootView;
    }
}
