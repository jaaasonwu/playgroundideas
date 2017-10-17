package com.playgroundideas.playgroundideas;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.playgroundideas.playgroundideas.datasource.local.Database;
import com.playgroundideas.playgroundideas.datasource.local.DesignDao;
import com.playgroundideas.playgroundideas.datasource.local.ManualDao;
import com.playgroundideas.playgroundideas.datasource.local.ProjectDao;
import com.playgroundideas.playgroundideas.datasource.local.UserDao;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Created by Ferdinand on 14/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class LocalPersistenceTest {
    private UserDao userDao;
    private ProjectDao projectDao;
    private ManualDao manualDao;
    private DesignDao designDao;
    private Database db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, Database.class).build();
        userDao = db.userDao();
        projectDao = db.projectDao();
        designDao = db.designDao();
        manualDao = db.manualDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }
}

