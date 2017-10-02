package com.playgroundideas.playgroundideas.datasource.local;

import android.os.Environment;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.DesignPictureFileInfo;
import com.playgroundideas.playgroundideas.model.ManualFileInfo;
import com.playgroundideas.playgroundideas.model.ProjectPictureFileInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

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

    public static void writeManualFile(ManualFileInfo manualFile, File downloaded) throws IOException{
        if(isExternalStorageWritable()) {
            // Create new file in the manuals directory in the user's public documents directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), R.string.relative_pdf_manuals_directory_name + "/" + manualFile.getName());

            file.createNewFile();
            copyFile(downloaded, file);
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be written because external storage is not mounted");
        }
    }

    public static File readManualFile(ManualFileInfo manualFile) throws UnsupportedOperationException{
        if(isExternalStorageReadable()) {
            // Get the file in the manuals directory in the user's public documents directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), R.string.relative_pdf_manuals_directory_name + "/" + manualFile.getName());
            return file;
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be read because external storage is not mounted");
        }
    }

    public static boolean isDownloaded(ManualFileInfo manualFileInfo) {
        if(isExternalStorageReadable()) {
            // Get the manuals directory in the user's public documents directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), R.string.relative_pdf_manuals_directory_name + "/" + manualFileInfo.getName());
            return file.exists();
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be read because external storage is not mounted");
        }
    }

    public static void writeDesignPictureFile(DesignPictureFileInfo designPictureFile, File downloaded) throws IOException{
        if(isExternalStorageWritable()) {
            // Create new file in the design pictures directory in the app's private directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), R.string.relative_design_pictures_directory_name + "/" + designPictureFile.getName());

            file.createNewFile();
            copyFile(downloaded, file);
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be written because external storage is not mounted");
        }
    }

    public static File readDesignPictureFile(DesignPictureFileInfo designPictureFile) throws UnsupportedOperationException{
        if(isExternalStorageReadable()) {
            // Get the file in the design pictures directory in the app's private directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), R.string.relative_design_pictures_directory_name + "/" + designPictureFile.getName());
            return file;
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be read because external storage is not mounted");
        }
    }

    public static void writeProjectPictureFile(ProjectPictureFileInfo projectPictureFile, File downloaded) throws IOException{
        if(isExternalStorageWritable()) {
            // Create new file in the project pictures directory in the app's private directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), R.string.relative_project_pictures_directory_name + "/" + projectPictureFile.getName());

            file.createNewFile();
            copyFile(downloaded, file);
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be written because external storage is not mounted");
        }
    }

    public static File readProjectPictureFile(ProjectPictureFileInfo projectPictureFile) {
        if(isExternalStorageReadable()) {
            // Get the file in the project pictures directory in the app's private directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), R.string.relative_project_pictures_directory_name + "/" + projectPictureFile.getName());
            return file;
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be read because external storage is not mounted");
        }
    }

    private static void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }
}
