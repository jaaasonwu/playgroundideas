package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
class GridViewAdapterFavorite extends BaseAdapter {

    class ViewHolder {
        private TextView desc;
        private ImageView image;
        private Button deleteButton;

        ViewHolder(View v){
            this.desc = (TextView) v.findViewById(R.id.textView);
            this.image = (ImageView) v.findViewById(R.id.imageView);
            this.deleteButton = (Button) v.findViewById(R.id.add_or_delete_button);
        }
    }

    private ArrayList<DesignItem> list;
    private Context context;

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
            holder.deleteButton.setText("delete button");
            designItem.setTag(holder);
        }
        else{
            holder = (ViewHolder) designItem.getTag();
        }

        DesignItem temp_item = list.get(i);

        holder.desc.setText(temp_item.getDescription());
        holder.image.setImageResource(temp_item.getImage());
        ButtonHandler buttonHandler = new ButtonHandler(i, temp_item);
        holder.image.setOnClickListener(buttonHandler);
        holder.deleteButton.setOnClickListener(buttonHandler);
        return designItem;

    }

}


