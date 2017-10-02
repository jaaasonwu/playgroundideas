package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
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

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;

/**
 * Created by Peter Chen on 2017/8/29.
 */
class GridViewAdapterFavorite extends BaseSwipeAdapter implements Filterable{

    CustomFilter1 filter;
    ArrayList<DesignItem> filterList;
    private ArrayList<DesignItem> list;
    private Context context;

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter1();
        }
        return filter;
    }

    protected class CustomFilter1 extends Filter {

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


    GridViewAdapterFavorite(Context context){
        list = new ArrayList<DesignItem>();
        this.context = context;
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
        public ButtonHandler(int i, DesignItem designItem)
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
                        toast = Toast.makeText(context, textItemNum +
                                " favorite design was removed.", Toast.LENGTH_SHORT);
                        toast.show();
                        filterList.remove(itemSeq);
                        notifyDataSetChanged();
                    break;

            }

        }
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.sl_content;
    }

    @Override
    public View generateView(int i, ViewGroup viewGroup) {
        View view = View.inflate(context, R.layout.design_item, null);
        return view;
    }

    @Override
    public void fillValues(int i, View view) {
        TextView desc;
        ImageView image;
        Button deleteButton;
        Button emailShare;
        Button facebookShare;
        final FloatingActionButton floatingActionPlus;
        final FloatingActionButton floatingActionEmailShare;
        final FloatingActionButton floatingActionFacebookShare;
        final Animation fabOpenShare, fabCloseShare, fabRClockwise, fabRAnticlockwise;
        final SwipeLayout sl_content;

        desc = (TextView) view.findViewById(R.id.textView);
        image = (ImageView) view.findViewById(R.id.imageView);
        deleteButton = (Button) view.findViewById(R.id.add_or_delete_button);
        deleteButton.setText("Delete");

        Iconify.with(new MaterialModule());
        floatingActionPlus = (FloatingActionButton) view.findViewById(R.id.fab_share);
        floatingActionEmailShare = (FloatingActionButton) view.findViewById(R.id.fab_email_share);
        floatingActionFacebookShare = (FloatingActionButton) view.findViewById(R.id.fab_facebook_share);
        floatingActionPlus.setImageDrawable(new IconDrawable(context, MaterialIcons.md_add)
                .colorRes(R.color.black).actionBarSize());
        floatingActionEmailShare.setImageDrawable(new IconDrawable(context, MaterialIcons.md_email)
                .colorRes(R.color.black).actionBarSize());
        floatingActionFacebookShare.setImageDrawable(new IconDrawable(context, MaterialIcons.md_share)
                .colorRes(R.color.black).actionBarSize());
        fabOpenShare = AnimationUtils.loadAnimation(context, R.anim.fab_open_share);
        fabRClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise);
        fabCloseShare = AnimationUtils.loadAnimation(context, R.anim.fab_close_share);
        fabRAnticlockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_anticlockwise);


        emailShare = (Button) view.findViewById(R.id.email_share);
        facebookShare = (Button) view.findViewById(R.id.facebook_share);
        sl_content = (SwipeLayout) view.findViewById(R.id.sl_content);
        sl_content.setShowMode(SwipeLayout.ShowMode.PullOut);

        DesignItem temp_item = list.get(i);
        desc.setText(temp_item.getDescription());
        image.setImageResource(temp_item.getImage());
        ButtonHandler buttonHandler = new ButtonHandler(i, temp_item);
        image.setOnClickListener(buttonHandler);
        deleteButton.setOnClickListener(buttonHandler);
        emailShare.setOnClickListener(buttonHandler);
        facebookShare.setOnClickListener(buttonHandler);
        floatingActionPlus.setOnClickListener(new View.OnClickListener() {
            boolean isOpen = false;
            @Override
            public void onClick(View view) {
                if(isOpen){
                    floatingActionEmailShare.startAnimation(fabCloseShare);
                    floatingActionFacebookShare.startAnimation(fabCloseShare);
                    floatingActionPlus.startAnimation(fabRAnticlockwise);
                    floatingActionEmailShare.setClickable(false);
                    floatingActionFacebookShare.setClickable(false);
                    isOpen = false;
                }
                else{
                    floatingActionEmailShare.startAnimation(fabOpenShare);
                    floatingActionFacebookShare.startAnimation(fabOpenShare);
                    floatingActionPlus.startAnimation(fabRClockwise);
                    floatingActionEmailShare.setClickable(true);
                    floatingActionFacebookShare.setClickable(true);
                    isOpen = true;
                }
            }
        });
    }
}


