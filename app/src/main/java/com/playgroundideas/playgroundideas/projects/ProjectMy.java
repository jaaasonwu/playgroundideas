package com.playgroundideas.playgroundideas.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;


import com.playgroundideas.playgroundideas.MainActivity;
import com.playgroundideas.playgroundideas.R;

/**
 * Created by TongNiu on 4/9/17.
 */

public class ProjectMy extends Fragment implements View.OnClickListener{


    private ImageButton mCreateBtn;
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.project_my, container, false);
        mCreateBtn = (ImageButton) rootView.findViewById(R.id.create_project);
        mCreateBtn.setOnClickListener(this);
        mainActivity =(MainActivity) getActivity();
        return rootView;
    }


    @Override
    public void onClick(View v) {
        mainActivity.respondCreate();
    }
}
