package com.playgroundideas.playgroundideas.manuals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.repository.ManualRepository;

import java.io.File;
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
    @Inject
    ManualRepository repo;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repo.updateManualInfo();

        Bundle bundle = getArguments();
        mGroupHeader =  bundle.getStringArrayList("groupHeader");
        mDownloadStatus = (HashMap<String, Boolean>)bundle.getSerializable("downloadStatus");
        mItemHeader = (HashMap<String, List<String>>)bundle.getSerializable("itemHeader");

        File folder = new File(String.valueOf(getContext().getExternalFilesDir(null)));
        for (String s : mGroupHeader) {
            File pdf = new File(folder.getAbsolutePath() + "/" + s + ".pdf");
            if (pdf.exists()) {
                mDownloadStatus.put(s, Boolean.TRUE);
            }
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
