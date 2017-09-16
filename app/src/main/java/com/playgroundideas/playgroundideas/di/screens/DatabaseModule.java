package com.playgroundideas.playgroundideas.di.screens;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.playgroundideas.playgroundideas.datasource.local.Database;
import com.playgroundideas.playgroundideas.datasource.local.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ferdinand on 16/09/2017.
 */

@Module
public class DatabaseModule {

    @Singleton @Provides
    Database provideDb(Application app) {
        return Room.databaseBuilder(app, Database.class,"playgroundIdeas.db").build();
    }

    @Singleton @Provides
    UserDao provideUserDao(Database db) {
        return db.userDao();
    }
}
