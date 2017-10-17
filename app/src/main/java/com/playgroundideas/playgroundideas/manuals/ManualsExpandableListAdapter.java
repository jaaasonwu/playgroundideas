package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualChapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ManualsExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ManualExpandableList mList;
    private List<Manual> mGroupHeader;
    private HashMap<Manual, List<ManualChapter>> mItemHeader;
    private HashMap<Manual, Boolean> mDownloadStatus;
    private final String baseUrl = "http://swen90014v-2017plp.cis.unimelb.edu.au:3000/";

    ManualsExpandableListAdapter(Context context, ManualExpandableList list) {
        this.mContext = context;
        this.mGroupHeader = new ArrayList<>();
        this.mItemHeader = new HashMap<>();
        this.mDownloadStatus = new HashMap<>();
        this.mList = list;
    }

    public void updateGroups(List<Manual> manuals) {
        // Update the download status and groups
        mGroupHeader.clear();
        mGroupHeader.addAll(manuals);
        for (Manual manual : manuals) {
            mDownloadStatus.put(manual, manual.isPDFDownloaded());
            //initialise item header with no manual chapters -> they will be filled later
            mItemHeader.put(manual, new LinkedList<ManualChapter>());
        }

        //Notify the adapter of the change to update the UI
        notifyDataSetChanged();
    }

    public void updateChildrenOfGroup(Manual manual, List<ManualChapter> chapters) {
        mItemHeader.get(manual).clear();
        mItemHeader.get(manual).addAll(chapters);
        notifyDataSetChanged();
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
        return mGroupHeader.get(i).getId();
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        Manual manual = (Manual) getGroup(i);
        // Inflate the parent list
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.manuals_list_group, null);
        }

        // Check the downloaded status to determine whether to show the download icon
        final TextView download = view.findViewById(R.id.manual_download);
        if (mDownloadStatus.get(mGroupHeader.get(i)) == Boolean.FALSE) {
            download.setOnClickListener(new OnDownloadClickListener(mGroupHeader.get(i)));
            download.setVisibility(View.VISIBLE);
        } else {
            download.setVisibility(View.INVISIBLE);
        }
        TextView headerView = view.findViewById(R.id.listTitle);
        headerView.setText(manual.getName());

        return view;
    }

    /**
     * This method defines the behavior when the download icon is pressed.
     */
    private class OnDownloadClickListener implements View.OnClickListener {
        private Manual manual;
        public OnDownloadClickListener(Manual manual) {
            this.manual = manual;
        }
        @Override
        public void onClick(View view) {
            mList.downloadManual(manual);
        }
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
        return ((ManualChapter) getChild(i, i1)).getPosition();
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final ManualChapter chapter = (ManualChapter) getChild(i, i1);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.manuals_list_item, null);
        }
        // Set the behavior when a chapter is clicked
        view.setOnClickListener(new onOpenManualChapterClick((ManualChapter) getChild(i, i1)));
        TextView itemText = view.findViewById(R.id.expandedListItem);
        itemText.setText(chapter.getTitle());
        return view;
    }

    /**
     * Defines the behavior when the manual chapter is clicked
     */
    private class onOpenManualChapterClick implements View.OnClickListener {
        private ManualChapter chapter;

        public onOpenManualChapterClick(ManualChapter chapter) {
            this.chapter = chapter;
        }

        @Override
        public void onClick(View view) {
            // Start an intent to open a web browser and use google docs preview to show the pdf
            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setDataAndType(Uri.parse("http://docs.google.com/gview?embedded=true&url=" +
                    baseUrl + "manual/" + chapter.getManualId() + "/chapter/" + chapter.getPosition() + "/html"), "text/html");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            mContext.startActivity(intent);
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
