package com.playgroundideas.playgroundideas;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.model.FileInfo;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Ferdinand on 14/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class FileStorageTest {

    @Test
    public void writeAndReadDesignImage() throws Exception{
        InputStream toWrite = TestUtil.createImage();
        String name = "test.jpg";
        FileInfo writeResult = FileStorage.writeDesignImageFile(name, toWrite, InstrumentationRegistry.getTargetContext());
        Assert.assertTrue(FileStorage.isDownloaded(writeResult));

        File readResult = FileStorage.readFile(writeResult);
        Assert.assertTrue(IOUtils.contentEquals(new FileInputStream(readResult), toWrite));
    }

    @Test
    public void writeAndReadProjectImage() throws Exception{
        InputStream toWrite = TestUtil.createImage();
        String name = "test.jpg";
        FileInfo writeResult = FileStorage.writeProjectImageFile(name, toWrite, InstrumentationRegistry.getTargetContext());
        Assert.assertTrue(FileStorage.isDownloaded(writeResult));

        File readResult = FileStorage.readFile(writeResult);
        Assert.assertTrue(IOUtils.contentEquals(new FileInputStream(readResult), toWrite));
    }

    @Test
    public void writeAndReadDesignGuide() throws Exception{
        InputStream toWrite = TestUtil.createText();
        String name = "test.txt";
        FileInfo writeResult = FileStorage.writeDesignGuideFile(name, toWrite, InstrumentationRegistry.getTargetContext());
        Assert.assertTrue(FileStorage.isDownloaded(writeResult));

        File readResult = FileStorage.readFile(writeResult);
        Assert.assertTrue(IOUtils.contentEquals(new FileInputStream(readResult), toWrite));
    }

    @Test
    public void writeAndReadManual() throws Exception{
        InputStream toWrite = TestUtil.createText();
        String name = "test.txt";
        FileInfo writeResult = FileStorage.writeManualFile(name, toWrite, InstrumentationRegistry.getTargetContext());
        Assert.assertTrue(FileStorage.isDownloaded(writeResult));

        File readResult = FileStorage.readFile(writeResult);
        Assert.assertTrue(IOUtils.contentEquals(new FileInputStream(readResult), toWrite));
    }
}
