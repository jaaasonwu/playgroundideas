package com.playgroundideas.playgroundideas.projects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.playgroundideas.playgroundideas.R;

import java.util.List;



public class ProjectsListAdapter extends BaseAdapter {


    private List<ProjectItem> mProject;
    private Context mContext;


    public ProjectsListAdapter(Context context, List<ProjectItem> data) {
        this.mContext = context;
        this.mProject = data;
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

        ViewHolder holder;

        if (converView == null) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            converView = inflater.inflate(R.layout.project_list_item, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) converView.findViewById(R.id.project_title);
            holder.imageProjectView = (ImageView) converView.findViewById(R.id.project_image);

            converView.setTag(holder);
        } else {

            holder = (ViewHolder) converView.getTag();
        }

        String ProjectTitle = mProject.get(position).getProjectTtile();
        holder.titleTextView.setText(ProjectTitle);
        Glide.with(mContext).load(mProject.get(position).getImageUrl())
                            .into(holder.imageProjectView);

        return converView;
    }

    public static class ViewHolder {
        TextView titleTextView;
        ImageView imageProjectView;
    }

}
