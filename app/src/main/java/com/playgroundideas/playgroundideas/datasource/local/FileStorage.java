package com.playgroundideas.playgroundideas.datasource.local;

import android.os.Environment;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.DesignPictureFileInfo;
import com.playgroundideas.playgroundideas.model.ManualFileInfo;
import com.playgroundideas.playgroundideas.model.ProjectPictureFileInfo;

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

    public static void writeManualFile(ManualFileInfo manualFile, InputStream downloaded) throws IOException{
        if(isExternalStorageWritable()) {
            // Create new file in the manuals directory in the user's public documents directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), R.string.relative_pdf_manuals_directory_name + "/" + manualFile.getName());

            file.createNewFile();
            writeStreamToFile(downloaded, file);
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

    public static DesignPictureFileInfo writeDesignPictureFile(DesignPictureFileInfo info, InputStream downloaded) throws IOException{
        if(isExternalStorageWritable()) {
            // Create new file in the design pictures directory in the app's private directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), R.string.relative_design_pictures_directory_name + "/" + info.getName());

            file.createNewFile();
            writeStreamToFile(downloaded, file);
            return info;
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

    public static void writeProjectPictureFile(ProjectPictureFileInfo projectPictureFile, InputStream downloaded) throws IOException{
        if(isExternalStorageWritable()) {
            // Create new file in the project pictures directory in the app's private directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), R.string.relative_project_pictures_directory_name + "/" + projectPictureFile.getName());

            file.createNewFile();
            writeStreamToFile(downloaded, file);
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

    private static boolean writeStreamToFile(InputStream inputStream, File file) {
        try {

            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                outputStream = new FileOutputStream(file);

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
