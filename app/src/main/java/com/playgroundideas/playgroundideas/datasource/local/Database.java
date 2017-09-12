package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.persistence.room.RoomDatabase;

import com.playgroundideas.playgroundideas.domain.Design;
import com.playgroundideas.playgroundideas.domain.Manual;
import com.playgroundideas.playgroundideas.domain.Plan;
import com.playgroundideas.playgroundideas.domain.Project;
import com.playgroundideas.playgroundideas.domain.User;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@android.arch.persistence.room.Database(entities = {User.class, Plan.class, Design.class, Manual.class, Project.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PlanDao planDao();
    public abstract ManualDao manualDao();
    public abstract DesignDao designDao();
    public abstract ProjectDao projectDao();
}
