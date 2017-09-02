package com.playgroundideas.playgroundideas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.playgroundideas.playgroundideas.PagerAdapter.LEFT;
import static com.playgroundideas.playgroundideas.PagerAdapter.RIGHT;

public class ManualsFragment extends Fragment {
    int position;
    private List<String> mGroupHeader;
    private HashMap<String, List<String>> mItemHeader;
    private ExpandableListView mManualsList;
    private ExpandableListAdapter mManualsListAdapter;
    public ManualsFragment () {

    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        position = args.getInt("position");
        View rootView = inflater.inflate(R.layout.fragment_manuals, container, false);
//        TextView text = rootView.findViewById(R.id.message);
        fillListData();
        mManualsList = rootView.findViewById(R.id.manuals_exp_list);
        mManualsListAdapter = new ManualsExpandableListAdapter(this.getContext(), mGroupHeader, mItemHeader);
        mManualsList.setAdapter(mManualsListAdapter);


        if (position == LEFT) {
//            text.setText(R.string.manuals);

        } else if (position == RIGHT) {
//            text.setText(R.string.offline_manuals);
        }
        return rootView;
    }
}