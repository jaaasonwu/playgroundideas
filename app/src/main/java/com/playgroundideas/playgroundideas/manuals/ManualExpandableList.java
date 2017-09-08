package com.playgroundideas.playgroundideas.manuals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.playgroundideas.playgroundideas.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManualExpandableList extends Fragment {

    private ExpandableListView mManualsList;
    private ExpandableListAdapter mManualsListAdapter;
    private ArrayList<String> mGroupHeader;
    private HashMap<String, Boolean> mDownloadStatus;
    private HashMap<String, List<String>> mItemHeader;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
