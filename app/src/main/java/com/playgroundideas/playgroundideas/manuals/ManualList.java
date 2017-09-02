package com.playgroundideas.playgroundideas.manuals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ferdinand on 2/09/2017.
 */

public class ManualList extends Fragment{

    private List<String> mGroupHeader;
    private HashMap<String, List<String>> mItemHeader;
    private ExpandableListView mManualsList;
    private ExpandableListAdapter mManualsListAdapter;

    private void fillListData() {
        mGroupHeader = new ArrayList<>();
        mItemHeader = new HashMap<>();

        mGroupHeader.add("Playground Starter");
        List<String> starter = new ArrayList<>();
        starter.add("Welcome");
        starter.add("Let's Build A Playground");
        starter.add("Site Plan");
        starter.add("Elements");
        starter.add("Join Us!");
        mItemHeader.put(mGroupHeader.get(0), starter);

        mGroupHeader.add("Builder's Handbook");
        List<String> builder = new ArrayList<>();
        builder.add("Welcome");
        builder.add("Let's Build A Playground");
        builder.add("Site Plan");
        builder.add("Elements");
        builder.add("Join Us!");
        mItemHeader.put(mGroupHeader.get(1), builder);

        mGroupHeader.add("Safety Handbook");
        List<String> safety = new ArrayList<>();
        safety.add("Welcome");
        safety.add("Let's Build A Playground");
        safety.add("Site Plan");
        safety.add("Elements");
        safety.add("Join Us!");
        mItemHeader.put(mGroupHeader.get(2), safety);

        mGroupHeader.add("Loose Parts Manual");
        List<String> looseParts = new ArrayList<>();
        looseParts.add("Welcome");
        looseParts.add("Let's Build A Playground");
        looseParts.add("Site Plan");
        looseParts.add("Elements");
        looseParts.add("Join Us!");
        mItemHeader.put(mGroupHeader.get(3), looseParts);

        mGroupHeader.add("Inclusive Design Manual");
        List<String> design = new ArrayList<>();
        design.add("Welcome");
        design.add("Let's Build A Playground");
        design.add("Site Plan");
        design.add("Elements");
        design.add("Join Us!");
        mItemHeader.put(mGroupHeader.get(4), design);

        mGroupHeader.add("Cut & Paste");
        List<String> cutPaste = new ArrayList<>();
        cutPaste.add("Welcome");
        cutPaste.add("Let's Build A Playground");
        cutPaste.add("Site Plan");
        cutPaste.add("Elements");
        cutPaste.add("Join Us!");
        mItemHeader.put(mGroupHeader.get(5), cutPaste);

        mGroupHeader.add("Teacher Training");
        List<String> training = new ArrayList<>();
        training.add("Welcome");
        training.add("Let's Build A Playground");
        training.add("Site Plan");
        training.add("Elements");
        training.add("Join Us!");
        mItemHeader.put(mGroupHeader.get(6), training);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.manual_list, container, false);

        fillListData();
        mManualsList = rootView.findViewById(R.id.manuals_exp_list);
        mManualsListAdapter = new ManualsExpandableListAdapter(this.getContext(), mGroupHeader, mItemHeader);
        mManualsList.setAdapter(mManualsListAdapter);

        return rootView;
    }
}