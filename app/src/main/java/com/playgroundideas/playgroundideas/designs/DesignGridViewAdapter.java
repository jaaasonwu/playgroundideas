package com.playgroundideas.playgroundideas.designs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.viewmodel.DesignListViewModel;

import java.util.List;

/**
 * Created by Peter Chen on 2017/8/29.
 */
class DesignGridViewAdapter extends ArrayAdapter<Pair<Design, Boolean>> {

    private DesignListViewModel viewModel;

    public DesignGridViewAdapter(Context context, List<Pair<Design, Boolean>> designs, DesignListViewModel viewModel) {
        super(context, R.layout.design_item, designs);
        this.viewModel = viewModel;
    }

    public void updateDesigns(List<Pair<Design, Boolean>> designs) {
        this.clear();
        this.addAll(designs);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        View designView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            designView = View.inflate(getContext(), R.layout.design_item, parent);
            viewHolder = new ViewHolder(convertView, i);
            designView.setTag(viewHolder);
        } else {
            designView = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.update(i);
        return designView;

    }

    private class OnFavourButtonClickListener implements View.OnClickListener {
        private int position;

        public OnFavourButtonClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            Pair<Design, Boolean> pair = getItem(position);
            viewModel.markAsFavourite(pair.first, !pair.second);
        }
    }

    class OnDesignClickListener implements View.OnClickListener {
        private int position;

        OnDesignClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), DesignDetailsActivity.class);
            intent.putExtra("designId", getItem(position).first.getId());
            getContext().startActivity(intent);
        }
    }

    private class ViewHolder {
        private TextView desc;
        private ImageView image;
        private TextView name;
        private Button favourButton;

        ViewHolder(View v, int position){
            this.desc = (TextView) v.findViewById(R.id.description);
            this.image = (ImageView) v.findViewById(R.id.image);
            this.name = (TextView) v.findViewById(R.id.name);
            this.favourButton = (Button) v.findViewById(R.id.favour_button);
            this.favourButton.setOnClickListener(new OnFavourButtonClickListener(position));
            v.setOnClickListener(new OnDesignClickListener(position));
            update(position);
        }

        void update(int position) {
            Pair<Design, Boolean> pair = getItem(position);
            Design design = pair.first;
            this.desc.setText(design.getDescription());
            this.image.setImageURI(Uri.fromFile(FileStorage.readDesignImageFile(design.getImageInfo())));
            this.name.setText(design.getName());
            this.favourButton.setBackgroundColor(100);
            if(pair.second) {
                this.favourButton.setBackgroundColor(2222);
            }
        }
    }
}


