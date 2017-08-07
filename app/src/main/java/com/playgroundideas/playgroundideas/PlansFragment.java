package com.playgroundideas.playgroundideas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.playgroundideas.playgroundideas.PagerAdapter.LEFT;
import static com.playgroundideas.playgroundideas.PagerAdapter.RIGHT;

public class PlansFragment extends Fragment {
    int position;
    public PlansFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        position = args.getInt("position");
        View rootView = inflater.inflate(R.layout.fragment_plans, container, false);
        TextView text = rootView.findViewById(R.id.message);

        if (position == LEFT) {
            text.setText(R.string.my_plans);
        } else if (position == RIGHT) {
            text.setText(R.string.brs_plans);
        }
        return rootView;
    }
}
