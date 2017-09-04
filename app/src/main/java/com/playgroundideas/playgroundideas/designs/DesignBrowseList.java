package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.playgroundideas.playgroundideas.R;

public class DesignBrowseList extends Fragment {

    GridView myGrid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_design_browse_list, container, false);
        myGrid = (GridView) view.findViewById(R.id.myGrid);
        myGrid.setAdapter(new GridViewAdapter(getActivity()));
        return view;
    }




}
