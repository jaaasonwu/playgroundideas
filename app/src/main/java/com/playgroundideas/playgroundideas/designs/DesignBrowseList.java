package com.playgroundideas.playgroundideas.designs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;

public class DesignBrowseList extends Fragment {

    private ArrayList<DesignItem> favoriteList;
    private GridView myGrid;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteList = new ArrayList<DesignItem>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_design_browse_list, container, false);
        myGrid = (GridView) view.findViewById(R.id.myGrid);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        final GridViewAdapterBrowse gridViewAdapterBrowse = new GridViewAdapterBrowse(getActivity(), favoriteList);
        myGrid.setAdapter(gridViewAdapterBrowse);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                gridViewAdapterBrowse.getFilter().filter(query);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
