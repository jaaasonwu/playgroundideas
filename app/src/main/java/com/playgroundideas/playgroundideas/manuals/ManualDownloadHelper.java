package com.playgroundideas.playgroundideas.manuals;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.remote.ManualWebservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ManualDownloadHelper extends AsyncTask<String, String, String> {
    private Context mContext;
    private HashMap<String, Boolean> mDownloadStatus;
    private TextView mDownload;
    private long mLength;
    private Handler mHandler;
    @Inject
    ManualWebservice webservice;

    public ManualDownloadHelper(Context context, HashMap<String, Boolean> downloadStatus, TextView download) {
        mContext = context;
        mDownloadStatus = downloadStatus;
        mDownload = download;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message input) {
                String msg = (String)input.obj;
                Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
                toast.show();
            }
        };
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
        OutputStream out = createOutStream(urlStr);
        Call<ResponseBody> call = webservice.getManual(urlStr[0]);
        Response res = null;
        try {
            res = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (res.isSuccessful()) {
            sendToastMessage(mContext.getString(R.string.download_start));
            ResponseBody body = (ResponseBody) res.body();
            InputStream input = body.byteStream();
            try {
                if (input == null) {
                    throw new IOException();
                }
            } catch (IOException e) {
                Log.e("Response body", "Cannot create input stream");
                e.printStackTrace();
            }
            mLength = body.contentLength();
            byte data[] = new byte[4096];
            long total = 0;
            int percentage = 0;
            try {
                while ((count = input.read(data)) != -1) {
                    total += count;
                    if ((int) ((total * 100) / mLength) - percentage >= 20) {
                        publishProgress(Integer.toString(percentage));
                        percentage += 20;
                    }

                    out.write(data, 0, count);
                }
                out.flush();
                out.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sendToastMessage(mContext.getString(R.string.download_failed));
        }
        return urlStr[0];
    }

    private void sendToastMessage(String str) {
        Message msg = Message.obtain();
        msg.obj = str;
        msg.setTarget(mHandler);
        msg.sendToTarget();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast toast = Toast.makeText(mContext, "Downloaded: " + values[0] + "%", Toast.LENGTH_SHORT);
        toast.show();
    }

    private OutputStream createOutStream(String... urlStr) {
        File folder = new File(String.valueOf(mContext.getExternalFilesDir(null)));
        File pdf = new File(folder.getAbsolutePath() + "/" + urlStr[0] + ".pdf");
        OutputStream out = null;

        try {
            if (!folder.exists()) {
                if (!folder.mkdir()) {
                    throw new IOException();
                }
            }
            if (!pdf.exists()) {
                if (!pdf.createNewFile()) {
                    throw new IOException();
                }

            }
            out = new FileOutputStream(pdf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
}
