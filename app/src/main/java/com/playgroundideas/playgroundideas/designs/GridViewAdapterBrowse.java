package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.DesignCategory;

import java.util.ArrayList;

/**
 * Created by Peter Chen on 2017/8/29.
 */

// This adpater is defined for filling data into each view, seraching designs in terms of keywords
// and categories in the design browsing page.
class GridViewAdapterBrowse extends BaseAdapter implements Filterable {

    CustomFilter filter;
    ArrayList<Design> filterList;
    String previousQuery = null;
    String catergory = "All";
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }
    // The holder used for optimizing the grid list perfomance.
    class ViewHolder {
        private TextView name;
        private ImageView image;
        private Button addButton;

        ViewHolder(View view){
            this.name = (TextView) view.findViewById(R.id.textView);
            this.image = (ImageView) view.findViewById(R.id.imageView);
            this.addButton = (Button) view.findViewById(R.id.add_or_delete_button);

        }
    }

    private ArrayList<Design> list;
    private Context context;
    private ArrayList<Design> favoriteList;

    GridViewAdapterBrowse(Context context, ArrayList<Design> favoriteList, CallbackManager callbackManager, ShareDialog shareDialog){
        list = new ArrayList<Design>();
        this.context = context;
        this.callbackManager = callbackManager;
        this.shareDialog = shareDialog;
        this.favoriteList = favoriteList;
        Resources resources = context.getResources();
        String[] name = resources.getStringArray(R.array.description);
        String[] imageUrls = {"https://playgroundideas.org/wp-content/uploads/design_gallery/Scoop and Shaft.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/Mayan Pyramid.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/tire hurdle.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/wide slide .jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/Twister_colour.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/stage_playgroundideas_colour.org_.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/slide tile.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/Scorpion.jpg"
        };
        DesignCategory[] catergory = {DesignCategory.BRIDGES.BRIDGES, DesignCategory.CLIMBING, DesignCategory.CP, DesignCategory.GROUNDLEVEL,
                DesignCategory.MUSICAL, DesignCategory.SEATING, DesignCategory.SEESAWS, DesignCategory.BRIDGES.SLIDES, DesignCategory.SWINGS,
                DesignCategory.SWINGS, DesignCategory.TUNNELS, DesignCategory.TE};

        for(int i = 0; i < name.length; i++){
            long id = (long) i;
            list.add(new Design(1, id, name[i], (long)1, catergory[i], "description", "Materials", true, true, "Safe", "Steps", "Tools", imageUrls[i]));
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

    //Handling the event of clicking a picture or an add button.
    class ButtonHandler implements View.OnClickListener {
        private int itemSeq;
        private Design designItem;
        public ButtonHandler(int i, Design designItem, ArrayList<Design> favoriteList)
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
                    intent.putExtra("designName", this.designItem.getName());
                    intent.putExtra("designDetail", this.designItem.getPictureUrl());
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

    // Fill data into each view.
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

        final Design temp_item = list.get(i);

        holder.name.setText(temp_item.getName());
        Glide.with(context).load(temp_item.getPictureUrl())
                .into(holder.image);
        ButtonHandler buttonHandler = new ButtonHandler(i, temp_item, favoriteList);
        holder.image.setOnClickListener(buttonHandler);
        holder.addButton.setOnClickListener(buttonHandler);
        return designItem;

    }

    // The definition of searching designs in terms of keywords and categories.
    protected class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           FilterResults results = new FilterResults();
            if(constraint != null && constraint.length() > 0){
                constraint = constraint.toString().toUpperCase();

                ArrayList<Design> filters = new ArrayList<>();

                for(int i = 0; i < filterList.size(); i++){
                    if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
                        Design item = filterList.get(i);
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

            if(!catergory.equalsIgnoreCase("All")) {
                ArrayList<Design> temp = (ArrayList<Design>) results.values;
                ArrayList<Design> filterResults = new ArrayList<>();
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getCategory().getDescription().equalsIgnoreCase(catergory)) {
                        Design item = temp.get(i);
                        filterResults.add(item);
                    }
                }
                results.count = filterResults.size();
                results.values = filterResults;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<Design>) results.values;
            notifyDataSetChanged();
        }
    }
}


