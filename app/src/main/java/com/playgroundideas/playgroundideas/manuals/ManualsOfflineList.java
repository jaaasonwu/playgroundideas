package com.playgroundideas.playgroundideas.manuals;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.viewmodel.ManualsListViewModel;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ManualsOfflineList extends DaggerFragment {
    private ListView mListView;
    private ManualsOfflineListAdapter mAdapter;
    private ManualsListViewModel viewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the manual ViewModel to get LiveData
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ManualsListViewModel.class);
        // Set the observer for manual LiveData to update the UI when database changes
        viewModel.getDownloadedManuals().observe(this, new Observer<List<Manual>>() {
            @Override
            public void onChanged(@Nullable List<Manual> manuals) {
                // Update the data in the adapter
                mAdapter.update(manuals);
            }
        });
    }

    public void removeDownloadedManual(Manual manual) {
        viewModel.makeDownloaded(manual, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.manuals_offline, container, false);
        mListView = rootView.findViewById(R.id.manual_offline_list);
        mAdapter = new ManualsOfflineListAdapter(getContext(), new LinkedList<Manual>(), this);
        // Binds the list with the adapter
        mListView.setAdapter(mAdapter);
        return rootView;

    }
}
