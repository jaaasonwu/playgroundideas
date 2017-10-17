package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.BuildConfig;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.model.Manual;

import java.io.File;
import java.util.List;

public class ManualsOfflineListAdapter extends ArrayAdapter<Manual> {
    private ManualsOfflineList offlineList;


    public ManualsOfflineListAdapter(Context context, List<Manual> manuals, ManualsOfflineList offlineList) {
        super(context, R.layout.manuals_offline_item, manuals);
        this.offlineList = offlineList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.manuals_offline_item, null);
            holder = new ViewHolder(convertView, getItem(position));
            // Use view holder for every item to avoid finding view every time
            convertView.setTag(holder);
        } else {
            // When there's a view holder bind to the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.update(getItem(position));
        return convertView;
    }

    public void update(List<Manual> manuals) {
        this.clear();
        this.addAll(manuals);
        this.notifyDataSetChanged();
    }

    /**
     * The class defines the behavior when the delete button is clicked on the list
     */
    private class OnDeleteButtonClick implements View.OnClickListener {
        private Manual manual;

        public OnDeleteButtonClick(Manual manual) {
            this.manual = manual;
        }

        @Override
        public void onClick(View view) {
            offlineList.removeDownloadedManual(manual);
        }
    }

    private class OnOpenManualClick implements View.OnClickListener {
        private Manual manual;

        public OnOpenManualClick(Manual manual) {
            this.manual = manual;
        }

        @Override
        public void onClick(View view) {
            // Open the pdf file using a pdf reader
            File file = FileStorage.readFile(manual.getPdfInfo());
            Intent intent = new Intent(Intent.ACTION_VIEW);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".fileProvider", file);
                intent.setDataAndType(uri, "application/pdf");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            }
            getContext().startActivity(intent);
        }
    }

    /**
     * Use a view holder to avoid find the views for every item
     */
    private class ViewHolder {
        private TextView titleText;
        private TextView deleteButton;

        public ViewHolder(View view, Manual manual) {
            titleText = view.findViewById(R.id.list_title);
            deleteButton = view.findViewById(R.id.manual_delete);
            // Set the behavior when the item is clicked and the delete button is clicked
            titleText.setOnClickListener(new OnOpenManualClick(manual));
            deleteButton.setOnClickListener(new OnDeleteButtonClick(manual));
            update(manual);
        }

        void update(Manual manual) {
            titleText.setText(manual.getName());
        }

    }
}
