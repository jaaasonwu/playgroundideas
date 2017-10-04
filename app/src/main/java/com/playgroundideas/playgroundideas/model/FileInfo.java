package com.playgroundideas.playgroundideas.model;

import android.support.annotation.NonNull;
import android.arch.persistence.room.Ignore;

/**
 * Created by Ferdinand on 12/09/2017.
 */

public class FileInfo {

    @NonNull
    private String name;
    private byte[] md5Hash;

    public FileInfo(String name, byte[] md5Hash) {
        this.name = name;
        this.md5Hash = md5Hash;
    }

    @Ignore
    public FileInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(byte[] md5Hash) {
        this.md5Hash = md5Hash;
    }
}
