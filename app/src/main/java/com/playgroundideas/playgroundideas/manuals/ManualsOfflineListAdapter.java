package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.BuildConfig;
import com.playgroundideas.playgroundideas.R;

import java.io.File;
import java.util.List;

public class ManualsOfflineListAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mDownloaded;
    private ManualsOfflineList mList;


    public ManualsOfflineListAdapter(Context context, int resource, List<String> mDownloaded,
                                     ManualsOfflineList list) {
        super(context, resource, mDownloaded);
        this.mContext = context;
        this.mDownloaded = mDownloaded;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.manuals_offline_item, null);
            holder = new ViewHolder(convertView);
            // Use view holder for every item to avoid finding view every time
            convertView.setTag(holder);
        } else {
            // When there's a view holder bind to the view
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the text for every item
        String title = mDownloaded.get(position);
        holder.titleText.setText(title);
        // Set the behavior when the item is clicked and the delete button is clicked
        holder.titleText.setOnClickListener(new OnOpenManualClick(title, mContext));
        holder.deleteButton.setOnClickListener(new OnDeleteButtonClick(this, position));
        return convertView;
    }


    public void setmDownloaded(List<String> downloaded) {
        mDownloaded = downloaded;
    }

    /**
     * The class defines the behavior when the delete button is clicked on the list
     */
    private class OnDeleteButtonClick implements View.OnClickListener {
        private ManualsOfflineListAdapter mAdapter;
        private int position;

        public OnDeleteButtonClick(ManualsOfflineListAdapter adapter, int position) {
            mAdapter = adapter;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            // First delete the file in the file storage
            String filename = mDownloaded.get(position);
            File folder = new File(String.valueOf(mContext.getExternalFilesDir(null)));
            File manual = new File(folder.getAbsolutePath() + "/" + filename + ".pdf");
            boolean deleted = manual.delete();
            // Then ask the list fragment to update the downloaded status in the database
            Message msg = Message.obtain();
            msg.obj = mDownloaded.get(position);
            mList.handleMessage(msg);
            // Finally update the UI
            if (deleted) {
                mDownloaded.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private class OnOpenManualClick implements View.OnClickListener {
        private String mFilename;
        private Context mContext;
        public OnOpenManualClick(String filename, Context context) {
            mFilename = filename;
            mContext = context;
        }

        @Override
        public void onClick(View view) {
            // Open the pdf file using a pdf reader
            File folder = new File(String.valueOf(mContext.getExternalFilesDir(null)));
            File manual = new File(folder.getAbsolutePath() + "/" + mFilename + ".pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", manual);
                intent.setDataAndType(uri, "application/pdf");
            } else {
                intent.setDataAndType(Uri.fromFile(manual), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            }
            mContext.startActivity(intent);
        }
    }

    /**
     * Use a view holder to avoid find the views for every item
     */
    private class ViewHolder {
        private TextView titleText;
        private TextView deleteButton;

        public ViewHolder(View view) {
            titleText = view.findViewById(R.id.list_title);
            deleteButton = view.findViewById(R.id.manual_delete);
        }

    }
}
