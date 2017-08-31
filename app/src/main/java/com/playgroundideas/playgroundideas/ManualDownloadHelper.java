package com.playgroundideas.playgroundideas;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jason Wu on 2017/8/31.
 */

public class ManualDownloadHelper extends AsyncTask<String, String, String> {
    private Context mContext;
    private int mLength;
    public ManualDownloadHelper(Context context) {
        mContext = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

//    @Override
//    protected void onPostExecute(String... url) {
//        super.onPostExecute(url[0]);
//    }

    @Override
    protected String doInBackground(String... urlStr) {
        int count;
        try {
            URL url = new URL(urlStr[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            mLength = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            File folder = new File(String.valueOf(mContext.getExternalFilesDir(null)));
            File pdf = new File(folder.getAbsolutePath() + "/starter.pdf");

            if (!folder.exists()) {
                folder.mkdir();
            }
            if (!pdf.exists()) {
                pdf.createNewFile();
            }
            OutputStream output = new FileOutputStream(pdf);

            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / mLength));

                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Cannot Read from URL: ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast toast = Toast.makeText(mContext, "Downloaded: " + values[0] + "%", Toast.LENGTH_SHORT);

    }
}
