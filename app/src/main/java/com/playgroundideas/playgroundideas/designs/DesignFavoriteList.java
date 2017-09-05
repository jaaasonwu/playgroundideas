package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;


public class DesignFavoriteList extends Fragment {

    private DesignsFragment designsFragment;
    private FloatingActionButton designsAddFab;
    private GridView myFavoriteGrid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_design_favorite_list, container, false);
        myFavoriteGrid = (GridView) view.findViewById(R.id.my_favorite_grid);
        myFavoriteGrid.setAdapter(new GridViewAdapterFavorite(getActivity()));
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.designsAddFab = (FloatingActionButton) getActivity().findViewById(R.id.add_designs_fab);
        designsAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                designsFragment = (DesignsFragment) getParentFragment();
                designsFragment.respond();
            }
        });

    }
}
