package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.content.res.Resources;
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
class GridViewAdapter extends BaseAdapter {

    class DesignItem{
        String description;
        int image;

        DesignItem(String desc, int image){
            this.description = desc;
            this.image = image;
        }
    }

    class View_Holder {
        TextView desc;
        ImageView image;
        Button addButton;

        View_Holder(View v){
            this.desc = (TextView) v.findViewById(R.id.textView);
            this.image = (ImageView) v.findViewById(R.id.imageView);
            this.addButton = (Button) v.findViewById(R.id.addButton);
        }
    }
    ArrayList<DesignItem> list;
    Context context;
    GridViewAdapter(Context context){
        list = new ArrayList<DesignItem>();
        this.context = context;
        Resources resources = context.getResources();
        String[] desc = resources.getStringArray(R.array.description);
        int[] images = {R.drawable.sample1, R.drawable.sample1, R.drawable.sample1, R.drawable.sample1, R.drawable.sample1,
                R.drawable.sample1, R.drawable.sample1, R.drawable.sample1};

        for(int i = 0; i < 8; i++){
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
        int item_seq;

        public ButtonHandler(int i) {
            this.item_seq = i;
        }

        @Override
        public void onClick(View view) {
            String text_item_num;
            Toast toast;
            int sequence = this.item_seq + 1;
            int residual = (sequence) % 10;
            if( residual == 1)
                text_item_num = sequence + " st";
            else if(residual == 2)
                text_item_num = sequence + " nd";
            else if(residual == 3)
                text_item_num = sequence + " rd";
            else
                text_item_num = sequence + " th";

            switch (view.getId()){
                case R.id.imageView:
                    toast = Toast.makeText(context, "The " + text_item_num + " design detail.", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case R.id.addButton:
                    toast = Toast.makeText(context, "The " + text_item_num + " favorite design is added.", Toast.LENGTH_SHORT);
                    toast.show();
                    break;

            }

        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View designItem = view;
        View_Holder holder = null;
        if(designItem == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            designItem = inflater.inflate(R.layout.design_item, viewGroup, false);
            holder = new View_Holder(designItem);
            designItem.setTag(holder);
        }
        else{
            holder = (View_Holder) designItem.getTag();
        }

        DesignItem temp_item = list.get(i);

        holder.desc.setText(temp_item.description);
        holder.image.setImageResource(temp_item.image);
        holder.image.setOnClickListener(new ButtonHandler(i));
        holder.addButton.setOnClickListener(new ButtonHandler(i));
        return designItem;

    }
}



