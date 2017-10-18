package com.playgroundideas.playgroundideas.di.screens;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.local.Database;
import com.playgroundideas.playgroundideas.datasource.local.DesignDao;
import com.playgroundideas.playgroundideas.datasource.local.ManualDao;
import com.playgroundideas.playgroundideas.datasource.local.ProjectDao;
import com.playgroundideas.playgroundideas.datasource.local.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton @Provides
    Database provideDb(Application application) {
        return Room.databaseBuilder(application, Database.class, R.string.database_file_name+"").fallbackToDestructiveMigration().build();
    }

    @Singleton @Provides
    UserDao provideUserDao(Database db) {
        return db.userDao();
    }

    @Singleton @Provides
    DesignDao provideDesignDao(Database db) {
        return db.designDao();
    }

    @Singleton @Provides
    ProjectDao provideProjectDao(Database db) {
        return db.projectDao();
    }

    @Singleton @Provides
    ManualDao provideManualDao(Database db) {
        return db.manualDao();
    }
}
