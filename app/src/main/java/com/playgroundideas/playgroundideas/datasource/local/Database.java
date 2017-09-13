package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.persistence.room.RoomDatabase;

import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.FavouritedDesignsPerUser;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.Project;
import com.playgroundideas.playgroundideas.model.ProjectPictureFile;
import com.playgroundideas.playgroundideas.model.User;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@android.arch.persistence.room.Database(entities = {User.class, Design.class, Manual.class, Project.class, FavouritedDesignsPerUser.class, ProjectPictureFile.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ManualDao manualDao();
    public abstract DesignDao designDao();
    public abstract ProjectDao projectDao();
}
