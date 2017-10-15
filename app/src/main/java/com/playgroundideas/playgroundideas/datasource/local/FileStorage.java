package com.playgroundideas.playgroundideas.datasource.local;

import android.content.Context;
import android.os.Environment;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.FileInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Singleton;

@Singleton
public class FileStorage {

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    public static boolean isDownloaded(FileInfo fileInfo) {
        if(isExternalStorageReadable()) {
            // check if the a file for the given file handle exists
            File file = new File(fileInfo.getPath());
            return file.exists();
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be read because external storage is not mounted");
        }
    }

    public static FileInfo writeManualFile(String name, InputStream downloaded, Context context) throws IOException{
        return writeFile(downloaded, Environment.DIRECTORY_DOCUMENTS, context.getString(R.string.relative_pdf_manuals_directory_name), name);
    }

    public static FileInfo writeDesignImageFile(String name, InputStream downloaded, Context context) throws IOException{
        return writeFile(downloaded, Environment.DIRECTORY_PICTURES, context.getString(R.string.relative_design_images_directory_name), name);
    }

    public static FileInfo writeDesignGuideFile(String name, InputStream downloaded, Context context) throws IOException{
        return writeFile(downloaded, Environment.DIRECTORY_DOCUMENTS, context.getString(R.string.relative_design_guides_directory_name), name);
    }

    public static FileInfo writeProjectImageFile(String name, InputStream downloaded, Context context) throws IOException{
        return writeFile(downloaded, Environment.DIRECTORY_PICTURES, context.getString(R.string.relative_project_images_directory_name), name);
    }

    public static File readFile(FileInfo fileInfo) throws UnsupportedOperationException{
        if(isExternalStorageReadable()) {
            // Get the file from the given file handle
            File file = new File(fileInfo.getPath());
            return file;
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be read because external storage is not mounted");
        }
    }

    private static FileInfo writeFile(InputStream downloaded, String environment, String subdirectory, String name) throws IOException{
        if(isExternalStorageWritable()) {
            // Create a new file in a subdirectory of the app's public directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    environment), "/" + subdirectory + "/" + name);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            writeStreamToFile(downloaded, file);
            return new FileInfo(name, file.getPath());
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be written because external storage is not mounted");
        }
    }

    private static boolean writeStreamToFile(InputStream inputStream, File file) {
        try {

            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                outputStream = new FileOutputStream(file, false);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}
