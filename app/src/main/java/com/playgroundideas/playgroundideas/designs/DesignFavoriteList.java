package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.playgroundideas.playgroundideas.R;


public class DesignFavoriteList extends Fragment implements View.OnClickListener {

    Button button;
    DesignsFragment designsFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_design_favorite_list, container, false);
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
