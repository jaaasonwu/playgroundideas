package com.playgroundideas.playgroundideas.designs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
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
        myFavoriteGrid = view.findViewById(R.id.my_favorite_grid);
        myFavoriteGrid.setAdapter(new GridViewAdapterFavorite(getActivity()));
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Iconify.with(new MaterialModule());
        this.designsAddFab = getActivity().findViewById(R.id.add_designs_fab);
        designsAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                designsFragment = (DesignsFragment) getParentFragment();
                designsFragment.respond();
            }
        });
        designsAddFab.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_shopping_cart)
                .colorRes(R.color.white).actionBarSize());

    }
}
