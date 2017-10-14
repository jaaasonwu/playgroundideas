package com.playgroundideas.playgroundideas.projects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.playgroundideas.playgroundideas.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class ProjectsListAdapter extends BaseAdapter implements Filterable{


    private List<ProjectItem> mProject;
    private List<ProjectItem> mSearchList;
    private String filterByCountry = "ALL";
    private String previousQuery = null;
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
            holder.dateTextView = (TextView) converView.findViewById(R.id.project_date);

            converView.setTag(holder);
        } else {

            holder = (ViewHolder) converView.getTag();
        }

        String ProjectTitle = mProject.get(position).getProjectTtile();
        String ProjectDate = new SimpleDateFormat("dd-MM-yyyy").format(mProject.get(position).getStartDate());
        holder.titleTextView.setText(ProjectTitle);
        holder.dateTextView.setText(ProjectDate);
        Glide.with(mContext).load(mProject.get(position).getImageUrl())
                            .into(holder.imageProjectView);

        return converView;
    }

    public static class ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        ImageView imageProjectView;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults oReturn = new FilterResults();
                ArrayList<ProjectItem> results = new ArrayList<ProjectItem>();
                ArrayList<ProjectItem> temp = new ArrayList<ProjectItem>();
                if (mSearchList == null) {
                    mSearchList = mProject;
                }
                if (charSequence != null) {
                    if (mSearchList != null && mSearchList.size() > 0) {
                        for (ProjectItem pro : mSearchList) {
                            if (pro.getProjectTtile().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                temp.add(pro);
                            }
                        }
                    }
                } else {
                    for (ProjectItem pro : mSearchList) {
                        temp.add(pro);
                    }
                }

                if(!filterByCountry.equalsIgnoreCase("all")) {
                    for (ProjectItem pro : temp) {
                        if (pro.getCountry().equalsIgnoreCase(filterByCountry)) {
                            results.add(pro);
                        }
                    }
                } else {
                    results = temp;
                }
                oReturn.count = results.size();
                oReturn.values = results;
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mProject = (ArrayList<ProjectItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setFilterByCountry(String filterByCountry) {
        this.filterByCountry = filterByCountry;
    }

    public void setPreviousQuery(String previousQuery) {
        this.previousQuery = previousQuery;
    }

    public String getPreviousQuery() {
        return previousQuery;
    }
}
