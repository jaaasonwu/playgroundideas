package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.R;

import java.util.HashMap;
import java.util.List;


public class ManualsExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mGroupHeader;
    private HashMap<String, List<String>> mItemHeader;
    private HashMap<String, Boolean> mDownloadStatus;

    ManualsExpandableListAdapter(Context context, List<String> groupHeader,
                                        HashMap<String, List<String>> itemHeader,
                                        HashMap<String, Boolean> mDownloadStatus) {
        this.mContext = context;
        this.mGroupHeader = groupHeader;
        this.mItemHeader = itemHeader;
        this.mDownloadStatus = mDownloadStatus;
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
            download.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    new ManualDownloadHelper(mContext, mDownloadStatus, download)
                            .execute(mGroupHeader.get(i));
                }
            });
            download.setVisibility(View.VISIBLE);
        } else {
            download.setVisibility(View.INVISIBLE);
        }
        TextView headerView = view.findViewById(R.id.listTitle);
        headerView.setText(headerText);

        return view;
    }


    @Override
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
        TextView itemText = view.findViewById(R.id.expandedListItem);

        itemText.setText(childText);
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
