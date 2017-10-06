package com.playgroundideas.playgroundideas.model;

/**
 * Created by Ferdinand on 3/10/2017.
 */

public abstract class VersionedEntity {
    private long version;

    public VersionedEntity() {
        version = 0;
    }

    public VersionedEntity(long version) {
        this.version = version;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
