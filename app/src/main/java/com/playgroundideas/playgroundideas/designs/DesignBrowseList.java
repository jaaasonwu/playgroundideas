package com.playgroundideas.playgroundideas.designs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;

public class DesignBrowseList extends Fragment {

    private ArrayList<DesignItem> fav_list;
    private GridView myGrid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fav_list = new ArrayList<DesignItem>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_design_browse_list, container, false);
        myGrid = (GridView) view.findViewById(R.id.myGrid);
        myGrid.setAdapter(new GridViewAdapterBrowse(getActivity(), fav_list));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
