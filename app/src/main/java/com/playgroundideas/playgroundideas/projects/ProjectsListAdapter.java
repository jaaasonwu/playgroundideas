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

import org.w3c.dom.Text;

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
    public ProjectItem getItem(int position) {
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
            holder.countryTextView = (TextView) converView.findViewById(R.id.location);
            holder.currentFundTextView = (TextView) converView.findViewById(R.id.current_funding);
            holder.goalFundTextView = (TextView) converView.findViewById(R.id.goal_funding);
            holder.dayLeftTextView = (TextView) converView.findViewById(R.id.percentage);
            holder.imageProjectView = (ImageView) converView.findViewById(R.id.project_image);

            converView.setTag(holder);
        } else {

            holder = (ViewHolder) converView.getTag();
        }

        String ProjectTitle = mProject.get(position).getProjectTtile();
        String currentFund = Integer.toString(mProject.get(position).getmCurrentFund());
        String goalFund = Integer.toString(mProject.get(position).getmGoalFund());
        String dayLeft = Integer.toString(mProject.get(position).getmDayleft());
        holder.titleTextView.setText(ProjectTitle);
        holder.countryTextView.setText(mProject.get(position).getCountry());
        holder.currentFundTextView.setText("$" + currentFund);
        holder.goalFundTextView.setText("$" + goalFund);
        holder.dayLeftTextView.setText(dayLeft);
        Glide.with(mContext).load(mProject.get(position).getImageUrl())
                            .into(holder.imageProjectView);

        return converView;
    }

    public static class ViewHolder {
        TextView titleTextView;
        TextView countryTextView;
        TextView currentFundTextView;
        TextView goalFundTextView;
        TextView dayLeftTextView;
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
