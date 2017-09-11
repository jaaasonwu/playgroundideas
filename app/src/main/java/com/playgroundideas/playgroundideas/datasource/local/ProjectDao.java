package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.playgroundideas.playgroundideas.domain.Project;

/**
 * Created by Ferdinand on 11/09/2017.
 */

@Dao
public interface ProjectDao {

    @Update
    public void update(Project project);
    @Delete
    public void delete(Project project);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertProject(Project project);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertProjects(Project... projects);

    @Query("SELECT * FROM project WHERE id = :id")
    LiveData<Project> load(long id);

    @Query("SELECT COUNT(1) FROM project WHERE id = :id")
    boolean hasProject(long id);
}
