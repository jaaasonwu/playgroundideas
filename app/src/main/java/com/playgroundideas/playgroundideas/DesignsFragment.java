package com.playgroundideas.playgroundideas;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import static com.playgroundideas.playgroundideas.PagerAdapter.LEFT;
import static com.playgroundideas.playgroundideas.PagerAdapter.RIGHT;

public class DesignsFragment extends Fragment implements View.OnClickListener{
    GridView myGrid;
    int position;
    Button button;
    MainActivity comm;
    // manager;
    public DesignsFragment () {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        position = args.getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fav_designs, container, false);
       // manager = getActivity().getSupportFragmentManager();
        if (position == LEFT) {
           // FragmentTransaction transaction = manager.beginTransaction();
            rootView = inflater.inflate(R.layout.fragment_fav_designs, container, false);
        } else if (position == RIGHT) {
            rootView = inflater.inflate(R.layout.fragment_br_designs, container, false);
            myGrid = (GridView) rootView.findViewById(R.id.myGrid);
            myGrid.setAdapter(new GridViewAdapter(getActivity()));
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.button = (Button) getActivity().findViewById(R.id.dbutton);
        button.setOnClickListener(this);
        comm = (MainActivity) getActivity();

    }

    @Override
    public void onClick(View view) {
        comm.respond();
    }
}