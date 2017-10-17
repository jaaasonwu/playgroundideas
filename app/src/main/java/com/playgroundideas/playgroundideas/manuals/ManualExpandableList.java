package com.playgroundideas.playgroundideas.manuals;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualChapter;
import com.playgroundideas.playgroundideas.viewmodel.ManualsListViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * This is the class for the expandable list in manual fragment
 */
public class ManualExpandableList extends DaggerFragment {

    private ExpandableListView mManualsList;
    private ManualsExpandableListAdapter mManualsListAdapter;
    private ManualsListViewModel viewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the view model and get the LiveData for manuals to update the UI
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ManualsListViewModel.class);

        // Add an observer to the LiveData to update UI when there's a change in DB
        viewModel.getNotDownloadedManuals().observe(this, new Observer<List<Manual>>() {
            @Override
            public void onChanged(@Nullable List<Manual> manuals) {
                mManualsListAdapter.updateGroups(manuals);
                for(final Manual manual : manuals) {
                    viewModel.getChaptersOf(manual).observe(getParentFragment(), new Observer<List<ManualChapter>>() {
                        @Override
                        public void onChanged(@Nullable List<ManualChapter> manualChapters) {
                            mManualsListAdapter.updateChildrenOfGroup(manual, manualChapters);
                        }
                    });
                }
            }
        });
    }

    public void downloadManual(Manual manual) {
        viewModel.makeDownloaded(manual, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.manual_expandable_list, container, false);
        mManualsList = rootView.findViewById(R.id.manuals_exp_list);
        mManualsListAdapter = new ManualsExpandableListAdapter(this.getContext(), this);
        mManualsList.setAdapter(mManualsListAdapter);

        return rootView;
    }

    /**
     * Update when the list is visible to the user
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mManualsListAdapter != null) {
            mManualsListAdapter.notifyDataSetChanged();
        }
    }
}
