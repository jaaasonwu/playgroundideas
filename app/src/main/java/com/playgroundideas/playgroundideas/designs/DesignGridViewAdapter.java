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
import com.playgroundideas.playgroundideas.model.User;
import com.playgroundideas.playgroundideas.viewmodel.DesignListViewModel;

import java.util.List;

/**
 * Created by Peter Chen on 2017/8/29.
 */
class DesignGridViewAdapter extends ArrayAdapter<Pair<Design, Boolean>> {

    private DesignListViewModel viewModel;
    private User user;

    public DesignGridViewAdapter(Context context, List<Pair<Design, Boolean>> designs, DesignListViewModel viewModel, User user) {
        super(context, R.layout.design_item, designs);
        this.viewModel = viewModel;
        this.user = user;
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
            viewHolder = new ViewHolder(convertView, getItem(i));
            designView.setTag(viewHolder);
        } else {
            designView = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.update(getItem(i));
        return designView;

    }

    private class OnFavourButtonClickListener implements View.OnClickListener {
        private Pair<Design, Boolean> pair;

        public OnFavourButtonClickListener(Pair<Design, Boolean> pair) {
            this.pair = pair;
        }

        @Override
        public void onClick(View view) {
            viewModel.markAsFavourite(pair.first, user, !pair.second);
        }
    }

    class OnDesignClickListener implements View.OnClickListener {
        private Design design;

        OnDesignClickListener(Design design) {
            this.design= design;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), DesignDetailsActivity.class);
            intent.putExtra("designId", design.getId());
            getContext().startActivity(intent);
        }
    }

    private class ViewHolder {
        private TextView desc;
        private ImageView image;
        private TextView name;
        private Button favourButton;

        ViewHolder(View v, Pair<Design, Boolean> pair){
            this.desc = v.findViewById(R.id.description);
            this.image =  v.findViewById(R.id.image);
            this.name =  v.findViewById(R.id.name);
            this.favourButton = v.findViewById(R.id.favour_button);
            this.favourButton.setOnClickListener(new OnFavourButtonClickListener(pair));
            v.setOnClickListener(new OnDesignClickListener(pair.first));
            update(pair);
        }

        void update(Pair<Design, Boolean> pair) {
            Design design = pair.first;
            this.desc.setText(design.getDescription());
            this.image.setImageURI(Uri.fromFile(FileStorage.readFile(design.getImageInfo())));
            this.name.setText(design.getName());
            this.favourButton.setBackgroundColor(100);
            if(pair.second) {
                this.favourButton.setBackgroundColor(2222);
            }
        }
    }
}


