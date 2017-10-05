package com.playgroundideas.playgroundideas.manuals;

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

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repo.updateManualInfo();

        mediator = new MediatorLiveData();

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ManualsListViewModel.class);
        viewModel.getAllManuals().observe(this, new Observer<List<Manual>>() {
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
            }
        });

        Bundle bundle = getArguments();
        mGroupHeader =  bundle.getStringArrayList("groupHeader");
        mDownloadStatus = (HashMap<String, Boolean>)bundle.getSerializable("downloadStatus");
        mItemHeader = (HashMap<String, List<String>>)bundle.getSerializable("itemHeader");

//        File folder = new File(String.valueOf(getContext().getExternalFilesDir(null)));
//        for (String s : mGroupHeader) {
//            File pdf = new File(folder.getAbsolutePath() + "/" + s + ".pdf");
//            if (pdf.exists()) {
//                mDownloadStatus.put(s, Boolean.TRUE);
//            }
//        }
    }

    private class onChapterChange implements Observer<List<ManualChapter>> {
        @Override
        public void onChanged(@Nullable List<ManualChapter> manualChapters) {
            String name = mGroupHeader.get(Integer.getInteger(manualChapters.get(0).getManualId().toString()));
            List<String> items = new ArrayList<>();
            for (ManualChapter m : manualChapters) {
                items.add(m.getTitle());
            }
            mItemHeader.put(name, items);
            mManualsListAdapter.setmItemHeader(mItemHeader);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.manual_expandable_list, container, false);
        mManualsList = rootView.findViewById(R.id.manuals_exp_list);
        mManualsListAdapter = new ManualsExpandableListAdapter(this.getContext(), mGroupHeader, mItemHeader, mDownloadStatus);
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
