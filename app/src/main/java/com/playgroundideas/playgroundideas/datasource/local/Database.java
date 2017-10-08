package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.persistence.room.RoomDatabase;

import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.DesignPictureFileInfo;
import com.playgroundideas.playgroundideas.model.FavouritedDesignsPerUser;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualChapter;
import com.playgroundideas.playgroundideas.model.Project;
import com.playgroundideas.playgroundideas.model.ProjectPictureFileInfo;
import com.playgroundideas.playgroundideas.model.User;

@android.arch.persistence.room.Database(entities = {User.class, Design.class, Manual.class, Project.class, ManualChapter.class, FavouritedDesignsPerUser.class, ProjectPictureFileInfo.class, DesignPictureFileInfo.class}, version = 4)
public abstract class Database extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ManualDao manualDao();
    public abstract DesignDao designDao();
    public abstract ProjectDao projectDao();

    //TODO remove this on release
    Database() {
        super();
        // populate database
        // TODO insert some entities
    }

}
