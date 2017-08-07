package com.playgroundideas.playgroundideas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.playgroundideas.playgroundideas.PagerAdapter.LEFT;
import static com.playgroundideas.playgroundideas.PagerAdapter.RIGHT;

public class DesignsFragment extends Fragment {
    int position;
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
        View rootView = inflater.inflate(R.layout.fragment_designs, container, false);
        TextView text = rootView.findViewById(R.id.message);

        if (position == LEFT) {
            text.setText(R.string.fav_designs);
        } else if (position == RIGHT) {
            text.setText(R.string.brs_designs);
        }
        return rootView;
    }
}
