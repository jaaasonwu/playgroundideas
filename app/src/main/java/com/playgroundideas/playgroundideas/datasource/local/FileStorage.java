package com.playgroundideas.playgroundideas.datasource.local;

import android.os.Environment;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.FileInfo;
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

    public static boolean isDownloaded(FileInfo FileInfo) {
        if(isExternalStorageReadable()) {
            // Get the manuals directory in the user's public documents directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), R.string.relative_pdf_manuals_directory_name + "/" + FileInfo.getName());
            return file.exists();
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be read because external storage is not mounted");
        }
    }

    public static FileInfo writeManualFile(FileInfo manualFile, InputStream downloaded) throws IOException{
        return writeFile(manualFile, downloaded, Environment.DIRECTORY_DOCUMENTS, R.string.relative_pdf_manuals_directory_name+"");
    }

    public static File readManualFile(FileInfo manualFile) throws UnsupportedOperationException{
        return readFile(manualFile, Environment.DIRECTORY_DOCUMENTS, R.string.relative_pdf_manuals_directory_name+"");
    }

    public static FileInfo writeDesignImageFile(FileInfo designImageInfo, InputStream downloaded) throws IOException{
        return writeFile(designImageInfo, downloaded, Environment.DIRECTORY_PICTURES, R.string.relative_design_pictures_directory_name+"");
    }

    public static File readDesignImageFile(FileInfo designImageInfo) throws UnsupportedOperationException{
        return readFile(designImageInfo, Environment.DIRECTORY_PICTURES, R.string.relative_design_pictures_directory_name+"");
    }

    public static FileInfo writeDesignGuideFile(FileInfo designGuideInfo, InputStream downloaded) throws IOException{
        return writeFile(designGuideInfo, downloaded, Environment.DIRECTORY_DOCUMENTS, R.string.relative_design_pictures_directory_name+"");
    }

    public static File readDesignGuideFile(FileInfo designGuideInfo) throws UnsupportedOperationException{
        return readFile(designGuideInfo, Environment.DIRECTORY_DOCUMENTS, R.string.relative_design_pictures_directory_name+"");
    }

    public static FileInfo writeProjectImageFile(ProjectPictureFileInfo projectPictureFile, InputStream downloaded) throws IOException{
        return writeFile(projectPictureFile, downloaded, Environment.DIRECTORY_PICTURES, R.string.relative_project_pictures_directory_name+"");
    }

    public static File readProjectImageFile(ProjectPictureFileInfo projectPictureFile) {
        return readFile(projectPictureFile, Environment.DIRECTORY_PICTURES, R.string.relative_project_pictures_directory_name+"");
    }

    private static FileInfo writeFile(FileInfo info, InputStream downloaded, String environment, String subdirectory) throws IOException{
        if(isExternalStorageWritable()) {
            // Create new file in the design pictures directory in the app's private directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    environment), subdirectory + "/" + info.getName());

            file.createNewFile();
            writeStreamToFile(downloaded, file);
            return info;
        } else {
            throw new UnsupportedOperationException("FileInfo cannot be written because external storage is not mounted");
        }
    }

    private static File readFile(FileInfo fileInfo, String environment, String subdirectory) throws UnsupportedOperationException{
        if(isExternalStorageReadable()) {
            // Get the file in the design pictures directory in the app's private directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(environment), subdirectory + "/" + fileInfo.getName());
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
