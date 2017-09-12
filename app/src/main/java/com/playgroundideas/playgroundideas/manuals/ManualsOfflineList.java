package com.playgroundideas.playgroundideas.manuals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ManualsOfflineList extends Fragment {
    private ListView mListView;
    private ManualsOfflineLIstAdapter mAdapter;
    private ArrayList<String> mGroupHeader;
    private HashMap<String, Boolean> mDownloadStatus;
    private List<String> mDownloaded;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mGroupHeader = bundle.getStringArrayList("groupHeader");
        mDownloadStatus = (HashMap<String, Boolean>) bundle.getSerializable("downloadStatus");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.manuals_offline, container, false);
        mListView = rootView.findViewById(R.id.manual_offline_list);

        mDownloaded = new ArrayList<>();
        for (String s : mGroupHeader) {
            if (mDownloadStatus.get(s)) {
                mDownloaded.add(s);
            }
        }
        mAdapter = new ManualsOfflineLIstAdapter(getContext(), mGroupHeader, mDownloadStatus, mDownloaded);
        mListView.setAdapter(mAdapter);
        return rootView;
        
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mAdapter != null) {
            mAdapter.update();
        }
    }
}
