package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class ManualDownloadHelper extends AsyncTask<String, String, String> {
    private Context mContext;
    private HashMap<String, Boolean> mDownloadStatus;
    private TextView mDownload;
    private int mLength;
    private final String URL_BASE = "http://192.168.1.107:3000/manuals";

    public ManualDownloadHelper(Context context, HashMap<String, Boolean> downloadStatus, TextView download) {
        mContext = context;
        mDownloadStatus = downloadStatus;
        mDownload = download;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast toast = Toast.makeText(mContext, "Downloaded Finished!", Toast.LENGTH_LONG);
        toast.show();
        mDownloadStatus.put(s, Boolean.TRUE);
        mDownload.setVisibility(View.INVISIBLE);
    }

    @Override
    protected String doInBackground(String... urlStr) {
        int count;
        try {
            URL url = new URL(URL_BASE + "/" + urlStr[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            mLength = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            File folder = new File(String.valueOf(mContext.getExternalFilesDir(null)));
            File pdf = new File(folder.getAbsolutePath() + "/" + urlStr[0] + ".pdf");

            if (!folder.exists()) {
                folder.mkdir();
            }
            if (!pdf.exists()) {
                pdf.createNewFile();
            } else {
                return urlStr[0];
            }
            OutputStream output = new FileOutputStream(pdf);

            byte data[] = new byte[4096];
            long total = 0;
            int percentage = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                if ((int) ((total * 100) / mLength) - percentage >= 20) {
                    publishProgress(Integer.toString(percentage));
                    percentage += 20;
                }

                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Cannot Read from URL: ", e.getMessage());
        }
        return urlStr[0];
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast toast = Toast.makeText(mContext, "Downloaded: " + values[0] + "%", Toast.LENGTH_SHORT);
        toast.show();
    }
}
