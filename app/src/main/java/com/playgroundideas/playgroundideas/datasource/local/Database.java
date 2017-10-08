package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.persistence.room.RoomDatabase;

import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.DesignPictureFileInfo;
import com.playgroundideas.playgroundideas.model.FavouritedDesign;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualChapter;
import com.playgroundideas.playgroundideas.model.Project;
import com.playgroundideas.playgroundideas.model.ProjectPictureFileInfo;
import com.playgroundideas.playgroundideas.model.User;

@android.arch.persistence.room.Database(entities = {User.class, Design.class, Manual.class, Project.class, ManualChapter.class, FavouritedDesign.class, ProjectPictureFileInfo.class, DesignPictureFileInfo.class}, version = 5)
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
