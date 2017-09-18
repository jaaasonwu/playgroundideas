package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;

/**
 * Created by Peter Chen on 2017/8/29.
 */
class GridViewAdapterBrowse extends BaseAdapter implements Filterable {

    CustomFilter filter;
    ArrayList<DesignItem> filterList;
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    class ViewHolder {
        private TextView desc;
        private ImageView image;
        private Button addButton;

        ViewHolder(View v){
            this.desc = (TextView) v.findViewById(R.id.textView);
            this.image = (ImageView) v.findViewById(R.id.imageView);
            this.addButton = (Button) v.findViewById(R.id.add_or_delete_button);
        }
    }

    private ArrayList<DesignItem> list;
    private Context context;
    private ArrayList<DesignItem> favoriteList;

    GridViewAdapterBrowse(Context context, ArrayList<DesignItem> favoriteList){
        list = new ArrayList<DesignItem>();
        this.context = context;
        this.favoriteList = favoriteList;
        Resources resources = context.getResources();
        String[] desc = resources.getStringArray(R.array.description);
        int[] images = {R.drawable.sample1, R.drawable.sample1, R.drawable.sample1, R.drawable.sample1, R.drawable.sample1,
                R.drawable.sample1, R.drawable.sample1, R.drawable.sample1};

        for(int i = 0; i < desc.length; i++){
            list.add(new DesignItem(desc[i], images[i]));
        }

        this.filterList = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ButtonHandler implements View.OnClickListener {
        private int itemSeq;
        private DesignItem designItem;
        public ButtonHandler(int i, DesignItem designItem, ArrayList<DesignItem> favoriteList)
        {
            this.itemSeq = i;
            this.designItem = designItem;
        }

        @Override
        public void onClick(View view) {
            String textItemNum;
            Toast toast;
            int sequence = this.itemSeq + 1;
            int residual = (sequence) % 10;
            if( residual == 1)
                textItemNum = sequence + " st";
            else if(residual == 2)
                textItemNum = sequence + " nd";
            else if(residual == 3)
                textItemNum = sequence + " rd";
            else
                textItemNum = sequence + " th";

            switch (view.getId()){
                case R.id.imageView:
                    toast = Toast.makeText(context, "The " + textItemNum + " design detail.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(context, DesignDetailsActivity.class);
                    intent.putExtra("designName", this.designItem.getDescription());
                    intent.putExtra("designDetail", this.designItem.getImage());
                    context.startActivity(intent);
                    break;
                case R.id.add_or_delete_button:
                    if(favoriteList.contains(this.designItem)){
                        toast = Toast.makeText(context, "Can not be added since the " + textItemNum +
                                " favorite design was added before.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        favoriteList.add(this.designItem);
                        toast = Toast.makeText(context, "The " + textItemNum + " favorite design is added.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;

            }

        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View designItem = view;
        ViewHolder holder = null;
        if(designItem == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            designItem = inflater.inflate(R.layout.design_item, viewGroup, false);
            holder = new ViewHolder(designItem);
            designItem.setTag(holder);
        }
        else{
            holder = (ViewHolder) designItem.getTag();
        }

        DesignItem temp_item = list.get(i);

        holder.desc.setText(temp_item.getDescription());
        holder.image.setImageResource(temp_item.getImage());
        ButtonHandler buttonHandler = new ButtonHandler(i, temp_item, favoriteList);
        holder.image.setOnClickListener(buttonHandler);
        holder.addButton.setOnClickListener(buttonHandler);
        return designItem;

    }

    protected class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           FilterResults results = new FilterResults();
            if(constraint != null && constraint.length() > 0){
                constraint = constraint.toString().toUpperCase();

                ArrayList<DesignItem> filters = new ArrayList<>();

                for(int i = 0; i < filterList.size(); i++){
                    if (filterList.get(i).getDescription().toUpperCase().contains(constraint)) {
                        DesignItem item = new DesignItem(filterList.get(i).getDescription(), filterList.get(i).getImage());
                        filters.add(item);
                    }
                    results.count = filters.size();
                    results.values =filters;
                }
            }
            else{
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<DesignItem>) results.values;
            notifyDataSetChanged();
        }
    }
}


