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

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ManualsOfflineLIstAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mGroupHeader;
    private HashMap<String, Boolean> downloadStatus;
    private List<String> mDownloaded;


    public ManualsOfflineLIstAdapter(Context context, List<String> groupHeader,
                                     HashMap<String, Boolean> downloadStatus,
                                     List<String> downloaded) {
        super(context, -1, downloaded);
        this.mContext = context;
        this.mGroupHeader = groupHeader;
        this.downloadStatus = downloadStatus;
        this.mDownloaded = downloaded;
    }

    public void update() {
        mDownloaded.clear();
        for (String s : mGroupHeader) {
            if (downloadStatus.get(s)) {
                mDownloaded.add(s);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.manuals_offline_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String title = mDownloaded.get(position);
        holder.titleText.setText(title);
        holder.titleText.setOnClickListener(new onOpenManualClick(title, mContext));
        holder.deleteButton.setOnClickListener(new onDeleteButtonClick(this, position));
        return convertView;
    }

    private class onDeleteButtonClick implements View.OnClickListener {
        private ManualsOfflineLIstAdapter mAdapter;
        private int position;

        public onDeleteButtonClick(ManualsOfflineLIstAdapter adapter, int position) {
            mAdapter = adapter;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            String filename = mDownloaded.get(position);
            File folder = new File(String.valueOf(mContext.getExternalFilesDir(null)));
            File manual = new File(folder.getAbsolutePath() + "/" + filename + ".pdf");
            boolean deleted = manual.delete();
            if (deleted) {
                downloadStatus.put(filename, Boolean.FALSE);
                mDownloaded.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private class onOpenManualClick implements View.OnClickListener {
        private String mFilename;
        private Context mContext;
        public onOpenManualClick(String filename, Context context) {
            mFilename = filename;
            mContext = context;
        }

        @Override
        public void onClick(View view) {
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


    private class ViewHolder {
        private TextView titleText;
        private TextView deleteButton;

        public ViewHolder(View view) {
            titleText = view.findViewById(R.id.list_title);
            deleteButton = view.findViewById(R.id.manual_delete);
        }

    }
}
