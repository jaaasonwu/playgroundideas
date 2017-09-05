package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;


public class DesignFavoriteList extends Fragment implements View.OnClickListener {

    private Button button;
    private DesignsFragment designsFragment;
    private ArrayList<DesignItem> fav_list;
    private GridView myGrid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey("fav_list")) {
            fav_list = savedInstanceState.getParcelableArrayList("fav_list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view;
        if(fav_list == null || fav_list.size() == 0) {
            view = inflater.inflate(R.layout.fragment_design_favorite_list, container, false);
        }
        else{
            view = inflater.inflate(R.layout.fragment_design_browse_list, container, false);
            myGrid = (GridView) view.findViewById(R.id.myGrid);
            myGrid.setAdapter(new GridViewAdapterFavorite(getActivity(), fav_list));
        }
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
        this.button = (Button) getActivity().findViewById(R.id.dbutton);
        button.setOnClickListener(this);
        designsFragment = (DesignsFragment) getParentFragment();

    }

    @Override
    public void onClick(View view) {

        designsFragment.respond();
    }
}
