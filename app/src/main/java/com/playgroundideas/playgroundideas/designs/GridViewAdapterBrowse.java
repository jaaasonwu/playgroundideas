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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.playgroundideas.playgroundideas.R;

import java.util.ArrayList;

/**
 * Created by Peter Chen on 2017/8/29.
 */
class GridViewAdapterBrowse extends BaseAdapter {

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
                    intent.putExtra("designName", this.designItem.description);
                    intent.putExtra("designDetail", this.designItem.image);
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

        holder.desc.setText(temp_item.description);
        holder.image.setImageResource(temp_item.image);
        ButtonHandler buttonHandler = new ButtonHandler(i, temp_item, favoriteList);
        holder.image.setOnClickListener(buttonHandler);
        holder.addButton.setOnClickListener(buttonHandler);
        return designItem;

    }
}


