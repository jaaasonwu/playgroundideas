package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
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
import com.joanzapata.iconify.widget.IconTextView;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.DesignCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Chen on 2017/8/29.
 */

// This adpater is defined for filling data into each view, seraching designs in terms of keywords
// and categories in the design page.
 class GridViewAdapter extends BaseAdapter implements Filterable{
    private CustomFilter filter1;
    String previousQuery = null;
    String catergory = "All";
    private ArrayList<Design> filterList;
    private ArrayList<Design> list;
    private Context context;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    static public ArrayList<Design> favoriteList = new ArrayList<Design>();
    private boolean isFavourite;
    private String[]imageUrls;
    GridViewAdapter(Context context,CallbackManager callbackManager, ShareDialog shareDialog, boolean isFavourite){
        list = new ArrayList<Design>();
        this.context = context;
        this.callbackManager = callbackManager;
        this.shareDialog = shareDialog;
        this.isFavourite = isFavourite;

        Resources resources = context.getResources();
        String[] name = resources.getStringArray(R.array.description);
        String[] imageUrlsSample = {"https://playgroundideas.org/wp-content/uploads/design_gallery/Scoop and Shaft.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/Mayan Pyramid.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/tire hurdle.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/wide slide .jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/Twister_colour.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/stage_playgroundideas_colour.org_.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/slide tile.jpg",
                "https://playgroundideas.org/wp-content/uploads/design_gallery/Scorpion.jpg"
        };
        this.imageUrls = imageUrlsSample;
        String description = "This play design may be added to cubbies for an extra element of fun " +
                "as children uses the scoop to carry materials to the top of the cubby and pour it down the shaft at the top.";
        DesignCategory[] catergory = {DesignCategory.BRIDGES.BRIDGES, DesignCategory.CLIMBING, DesignCategory.CP, DesignCategory.GROUNDLEVEL,
                DesignCategory.MUSICAL, DesignCategory.SEATING, DesignCategory.SEESAWS, DesignCategory.BRIDGES.SLIDES, DesignCategory.SWINGS,
                DesignCategory.SWINGS, DesignCategory.TUNNELS, DesignCategory.TE};

        for(int i = 0; i < name.length; i++){
            long id = (long) i;
            list.add(new Design(1, id, name[i], (long)1, catergory[i], description, "Materials", true, true, "Safe", "Steps", "Tools"));
        }
        this.filterList = list;

        if(isFavourite){
            this.filterList = favoriteList;
            this.list = favoriteList;
        }

    }

    // The definition of searching designs in terms of keywords and categories.
    protected class CustomFilter extends Filter {
        FilterResults results;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            results = new FilterResults();
            if(constraint != null && constraint.length() > 0){
                constraint = constraint.toString().toUpperCase();

                ArrayList<Design> filters = new ArrayList<>();

                for(int i = 0; i < filterList.size(); i++){
                    if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
                        Design item = filterList.get(i);
                        filters.add(item);
                    }
                    results.count = filters.size();
                    results.values = filters;
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

    @Override
    public Filter getFilter() {
        if(filter1 == null){
            filter1 = new CustomFilter();
        }
        return filter1;
    }

    // The holder used for optimizing the grid list perfomance.
    private class ViewHolder {
        private TextView name;
        private ImageView image;
        private IconTextView favouriteIcon;

        ViewHolder(View view){
            name = (TextView) view.findViewById(R.id.textView);
            image = (ImageView) view.findViewById(R.id.imageView);
            favouriteIcon = (IconTextView) view.findViewById(R.id.favourite_or_remove);
        }
    }

    //Handling the event of clicking a picture or a delete button.
    class ButtonHandler implements View.OnClickListener {
        private int itemSeq;
        private Design designItem;
        private ViewHolder holder;
        public ButtonHandler(int i, Design designItem, ViewHolder holder)
        {
            this.itemSeq = i;
            this.designItem = designItem;
            this.holder = holder;
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
                    intent.putExtra("designDetail",  imageUrls[itemSeq]);
                    intent.putExtra("designer", "playgroundIdeas");
                    intent.putExtra("category", this.designItem.getCategory().getDescription());
                    intent.putExtra("designDescription", this.designItem.getDescription());
                    intent.putExtra("buildingSteps", this.designItem.getBuildingSteps());
                    intent.putExtra("materials", this.designItem.getBuildingMaterials());
                    intent.putExtra("tools", this.designItem.getBuildingTools());
                    context.startActivity(intent);
                    break;
                case R.id.favourite_or_remove:
                    if(isFavourite) {
                        toast = Toast.makeText(context, "The " + textItemNum +
                                " favorite design was removed.", Toast.LENGTH_SHORT);
                        filterList.remove(itemSeq);
                        notifyDataSetChanged();
                        toast.show();
                    }
                    else {
                        if(favoriteList.contains(this.designItem)){
                            toast = Toast.makeText(context, "The " + textItemNum +
                                    " design is not a favourite design any more.", Toast.LENGTH_SHORT);
                            holder.favouriteIcon.setText("{mdi-star-outline}");
                            favoriteList.remove(this.designItem);
                            toast.show();
                        }
                        else{
                            favoriteList.add(this.designItem);
                            holder.favouriteIcon.setText("{mdi-star}");
                            notifyDataSetChanged();
                            toast = Toast.makeText(context, "The " + textItemNum + " design is added as a favourite one.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    break;

            }

        }
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

    // Fill data into each view.
    public View getView(int i, View view, ViewGroup viewGroup) {

        View designItem = view;
        final int itemSeq = i;
        ViewHolder holder = null;
        if(designItem == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            designItem = inflater.inflate(R.layout.design_item, viewGroup, false);
            holder = new ViewHolder(designItem);
            if(isFavourite) {
                holder.favouriteIcon.setText("{mdi-star}");
            }
            else{
                if(favoriteList.contains(filterList.get(itemSeq))){
                    holder.favouriteIcon.setText("{mdi-star}");
                }
                else{
                    holder.favouriteIcon.setText("{mdi-star-outline}");
                }
            }
            designItem.setTag(holder);
        }
        else{
            holder = (ViewHolder) designItem.getTag();
            if(favoriteList.contains(filterList.get(itemSeq))){
                holder.favouriteIcon.setText("{mdi-star}");
            }
            else{
                holder.favouriteIcon.setText("{mdi-star-outline}");
            }
        }

        final Design temp_item = list.get(i);
        holder.name.setText(temp_item.getName());
        Glide.with(context).load(imageUrls[i])
                .into(holder.image);
        ButtonHandler buttonHandler = new ButtonHandler(i, temp_item, holder);
        holder.image.setOnClickListener(buttonHandler);
        holder.favouriteIcon.setOnClickListener(buttonHandler);
        return  designItem;
    }

    public void designItemsChanged(List<Design> designs) {
        this.filterList = (ArrayList<Design>) designs;
        this.list = (ArrayList<Design>) designs;
        notifyDataSetChanged();
    }
}


