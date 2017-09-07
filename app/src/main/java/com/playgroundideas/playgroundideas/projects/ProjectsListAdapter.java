package com.playgroundideas.playgroundideas.projects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.playgroundideas.playgroundideas.R;
import java.util.List;


public class ProjectsListAdapter extends BaseAdapter {


    private List<String> mProject;
    private Context mContext;


    public ProjectsListAdapter(Context context, List<String> data) {
        mContext = context;
        mProject = data;
    }

    @Override
    public int getCount() {
        return mProject.size();
    }

    @Override
    public Object getItem(int position) {
        return mProject.get(position);
    }


    public long getItemId(int position) {
        return position;
    }

    @Override
    public  View getView(int position, View converView, ViewGroup parent) {
        View rootView;

        if (converView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rootView = inflater.inflate(R.layout.project_list_item, parent, false);
            rootView.setTag(position);
        } else {
            rootView = converView;
        }

        String item = mProject.get(position);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.project_title);
        titleTextView.setText(item);

        return rootView;
    }

}
