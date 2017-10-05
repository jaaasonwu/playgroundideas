package com.playgroundideas.playgroundideas.designs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;
import com.facebook.FacebookSdk;
/**
 * Created by Peter Chen on 2017/8/29.
 */
class GridViewAdapterFavorite extends BaseAdapter implements Filterable{

    private CustomFilter1 filter;
    private ArrayList<DesignItem> filterList;
    private ArrayList<DesignItem> list;
    private Context context;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

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

    private class ViewHolder {
        private TextView desc;
        private ImageView image;
        private Button deleteButton;
        private ImageView emailShare;
        private FloatingActionButton floatingActionPlus;
        private FloatingActionButton floatingActionEmailShare;
        private FloatingActionButton floatingActionFacebookShare;
        private Animation fabOpenShare, fabCloseShare, fabRClockwise, fabRAnticlockwise;

        ViewHolder(View view){
            desc = (TextView) view.findViewById(R.id.textView);
            image = (ImageView) view.findViewById(R.id.imageView);
            deleteButton = (Button) view.findViewById(R.id.add_or_delete_button);
            floatingActionPlus = (FloatingActionButton) view.findViewById(R.id.fab_share);
            floatingActionEmailShare = (FloatingActionButton) view.findViewById(R.id.fab_email_share);
            floatingActionFacebookShare = (FloatingActionButton) view.findViewById(R.id.fab_facebook_share);

            Iconify.with(new MaterialModule());

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

    GridViewAdapterFavorite(Context context,CallbackManager callbackManager, ShareDialog shareDialog){
        list = new ArrayList<DesignItem>();
        this.context = context;
        this.callbackManager = callbackManager;
        this.shareDialog = shareDialog;

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


    public View getView(int i, View view, ViewGroup viewGroup) {

        View designItem = view;
        ViewHolder holder = null;
        if(designItem == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            designItem = inflater.inflate(R.layout.design_item, viewGroup, false);
            holder = new ViewHolder(designItem);
            holder.deleteButton.setText("Delete");
            designItem.setTag(holder);
        }
        else{
            holder = (ViewHolder) designItem.getTag();
        }

        final DesignItem temp_item = list.get(i);
        holder.desc.setText(temp_item.getDescription());
        holder.image.setImageResource(temp_item.getImage());
        ButtonHandler buttonHandler = new ButtonHandler(i, temp_item);
        holder.image.setOnClickListener(buttonHandler);
        holder.deleteButton.setOnClickListener(buttonHandler);


        holder.floatingActionEmailShare.setOnClickListener(new View.OnClickListener(){
            Intent intent = null, chooser = null;
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                String[] to = {};
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, temp_item.getDescription() + " Sharing");
                intent.putExtra(Intent.EXTRA_TEXT, temp_item.getDescription());
                intent.setType("message/rfc822");
                chooser = Intent.createChooser(intent, "Send Email");
                context.startActivity(chooser);
            }
        });

        holder.floatingActionFacebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    Bitmap image = BitmapFactory.decodeResource(context.getResources(), temp_item.getImage());
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(image)
                            .build();
                    SharePhotoContent photoContent = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    shareDialog.show(photoContent);
                }

            }
        });
        return  designItem;
    }

}


