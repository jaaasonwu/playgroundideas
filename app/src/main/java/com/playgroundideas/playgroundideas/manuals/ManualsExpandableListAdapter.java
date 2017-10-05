package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ManualsExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ManualExpandableList mList;
    private List<String> mGroupHeader;
    private HashMap<String, List<String>> mItemHeader;
    private HashMap<String, Boolean> mDownloadStatus;
    private final String baseUrl = "http://swen90014v-2017plp.cis.unimelb.edu.au:3000/";

    ManualsExpandableListAdapter(Context context, ManualExpandableList list) {
        this.mContext = context;
        this.mGroupHeader = new ArrayList<>();
        this.mItemHeader = new HashMap<>();
        this.mDownloadStatus = new HashMap<>();
        this.mList = list;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public int getGroupCount() {
        return mItemHeader.size();
    }

    @Override
    public Object getGroup(int i) {
        return mGroupHeader.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        String headerText = (String) getGroup(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.manuals_list_group, null);
        }

        final TextView download = view.findViewById(R.id.manual_download);
        if (mDownloadStatus.get(mGroupHeader.get(i)) == Boolean.FALSE) {
            download.setOnClickListener(new OnDownloadClickListener(mGroupHeader.get(i)));
            download.setVisibility(View.VISIBLE);
        } else {
            download.setVisibility(View.INVISIBLE);
        }
        TextView headerView = view.findViewById(R.id.listTitle);
        headerView.setText(headerText);

        return view;
    }

    private class OnDownloadClickListener implements View.OnClickListener {
        String name;
        public OnDownloadClickListener(String name) {
            this.name = name;
        }
        @Override
        public void onClick(View view) {
            Message msg = Message.obtain();
            msg.arg1 = 0;
            msg.obj = name;
            mList.handleMessage(msg);
        }
    }

    public int getChildrenCount(int i) {
        return mItemHeader.get(mGroupHeader.get(i)).size();
    }

    @Override
    public Object getChild(int i, int i1) {
        return mItemHeader.get(mGroupHeader.get(i)).get(i1);
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String) getChild(i, i1);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.manuals_list_item, null);
        }
        view.setOnClickListener(new onOpenManualClick((String) getGroup(i), i1));
        TextView itemText = view.findViewById(R.id.expandedListItem);
        itemText.setText(childText);
        return view;
    }

    private class onOpenManualClick implements View.OnClickListener {
        private String mFilename;
        private int mId;
        public onOpenManualClick(String filename, int id) {
            mFilename = filename;
            mId = id;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setDataAndType(Uri.parse("http://docs.google.com/gview?embedded=true&url=" +
                    baseUrl + "manuals/" + mFilename + '/' + Integer.toString(mId)), "text/html");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            mContext.startActivity(intent);
        }
    }

    public void setmItemHeader(HashMap<String, List<String>> mItemHeader) {
        this.mItemHeader = mItemHeader;
    }

    public void setmGroupHeader(List<String> mGroupHeader) {
        this.mGroupHeader = mGroupHeader;
    }

    public void setmDownloadStatus(HashMap<String, Boolean> mDownloadStatus) {
        this.mDownloadStatus = mDownloadStatus;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
