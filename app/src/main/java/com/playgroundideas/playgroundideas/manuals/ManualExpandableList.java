package com.playgroundideas.playgroundideas.manuals;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
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
import com.playgroundideas.playgroundideas.datasource.repository.ManualRepository;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualChapter;
import com.playgroundideas.playgroundideas.viewmodel.ManualsListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ManualExpandableList extends DaggerFragment {

    private ExpandableListView mManualsList;
    private ManualsExpandableListAdapter mManualsListAdapter;
    private ArrayList<String> mGroupHeader;
    private HashMap<String, Boolean> mDownloadStatus;
    private HashMap<String, List<String>> mItemHeader;
    private ManualsListViewModel viewModel;
    MediatorLiveData mediator;
    @Inject
    ManualRepository repo;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    Executor executor;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                repo.updateManualInfo();
            }
        });

        mediator = new MediatorLiveData();

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ManualsListViewModel.class);
        final LiveData<List<Manual>> manualLiveData = viewModel.getAllManuals();
        manualLiveData.observe(this, new Observer<List<Manual>>() {
            @Override
            public void onChanged(@Nullable List<Manual> manuals) {
                String name;
                boolean downloaded;

                for (int i = 0; i < manuals.size(); i++) {
                    mGroupHeader.set(i, manuals.get(i).getName());
                    name = manuals.get(i).getName();
                    downloaded = manuals.get(i).getDownloaded();
                    mDownloadStatus.put(name, downloaded);
                }
                mManualsListAdapter.setmGroupHeader(mGroupHeader);
                mManualsListAdapter.setmDownloadStatus(mDownloadStatus);
                mManualsListAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getChapters().observe(this, new Observer<List<ManualChapter>>() {
            @Override
            public void onChanged(@Nullable List<ManualChapter> chapters) {
                List<Manual> manuals = manualLiveData.getValue();
                HashMap<Long, String> manualMap = new HashMap<>();
                HashMap<String, List<String>> itemHeader = new HashMap<>();
                for (Manual m : manuals) {
                    if (itemHeader.get(m.getName()) == null) {
                        itemHeader.put(m.getName(), new ArrayList<String>());
                        manualMap.put(m.getId(), m.getName());
                    }
                }
                for (ManualChapter c : chapters) {
                    itemHeader.get(manualMap.get(c.getManualId())).add(c.getTitle());
                }
                mItemHeader = itemHeader;
                mManualsListAdapter.setmItemHeader(mItemHeader);
                mManualsListAdapter.notifyDataSetChanged();
            }
        });

        Bundle bundle = getArguments();
        mGroupHeader =  bundle.getStringArrayList("groupHeader");
        mDownloadStatus = (HashMap<String, Boolean>)bundle.getSerializable("downloadStatus");
        mItemHeader = (HashMap<String, List<String>>)bundle.getSerializable("itemHeader");
    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.manual_expandable_list, container, false);
        mManualsList = rootView.findViewById(R.id.manuals_exp_list);
        mManualsListAdapter = new ManualsExpandableListAdapter(this.getContext());
        mManualsList.setAdapter(mManualsListAdapter);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mManualsListAdapter != null) {
            mManualsListAdapter.notifyDataSetChanged();
        }
    }
}
