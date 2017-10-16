package com.playgroundideas.playgroundideas.plans;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.playgroundideas.playgroundideas.R;

public class PlansFragment extends Fragment {
    private Button planButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_plans, container, false);
        planButton = rootView.findViewById(R.id.plan_button);
        planButton.setOnClickListener(new PlanButtonClickListener());
        return rootView;
    }

    private class PlanButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // TODO open an external app
        }
    }

}
