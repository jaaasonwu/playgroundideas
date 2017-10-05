package com.playgroundideas.playgroundideas.manuals;

import android.arch.lifecycle.LiveData;
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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ManualsOfflineList extends DaggerFragment {
    private ListView mListView;
    private ManualsOfflineListAdapter mAdapter;
    private ArrayList<String> mGroupHeader;
    private List<String> mDownloaded;
    private ManualsListViewModel viewModel;
    private LiveData<List<Manual>> manualLiveData;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGroupHeader = new ArrayList<>();
        mDownloaded = new ArrayList<>();
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ManualsListViewModel.class);
        manualLiveData = viewModel.getAllManuals();
        manualLiveData.observe(this, new Observer<List<Manual>>() {
            @Override
            public void onChanged(@Nullable List<Manual> manuals) {
                String name;
                boolean downloaded;

                mDownloaded.clear();
                for (int i = 0; i < manuals.size(); i++) {
                    mGroupHeader.add(manuals.get(i).getName());
                    name = manuals.get(i).getName();
                    downloaded = manuals.get(i).getDownloaded();
                    if (downloaded) {
                        mDownloaded.add(name);
                    }
                }
                mAdapter.setmDownloaded(mDownloaded);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.manuals_offline, container, false);
        mListView = rootView.findViewById(R.id.manual_offline_list);
        mAdapter = new ManualsOfflineListAdapter(getContext(), R.layout.manuals_offline_item, mDownloaded);
        mListView.setAdapter(mAdapter);
        return rootView;

    }
}
