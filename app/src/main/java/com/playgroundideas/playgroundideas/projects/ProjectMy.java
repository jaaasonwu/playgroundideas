package com.playgroundideas.playgroundideas.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import com.joanzapata.iconify.widget.IconButton;
import com.playgroundideas.playgroundideas.MainActivity;
import com.playgroundideas.playgroundideas.R;

/**
 * Created by TongNiu on 4/9/17.
 */

public class ProjectMy extends Fragment {


    private IconButton mCreateBtn;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.project_my, container, false);
        //use icon button
        mCreateBtn = (IconButton) rootView.findViewById(R.id.create_project);
        mCreateBtn.setOnClickListener(new IconButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProject();
            }
        });
        return rootView;
    }

    public void createProject() {
        Intent intent = new Intent();
        intent.setClass(getContext(), CreateProjectActivity.class);
        startActivity(intent);
    }
}
