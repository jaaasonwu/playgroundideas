package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.playgroundideas.playgroundideas.model.Project;
import com.playgroundideas.playgroundideas.model.ProjectPictureFileInfo;

import java.util.List;

/**
 * Created by Ferdinand on 11/09/2017.
 */

@Dao
public interface ProjectDao {

    @Update
    public void update(Project project);
    @Delete
    public void delete(Project project);
    @Insert(onConflict = OnConflictStrategy.FAIL)
    public long insert(Project project);

    @Query("SELECT * FROM project WHERE id = :id")
    LiveData<Project> load(long id);

    @Query("SELECT * FROM project")
    LiveData<List<Project>> loadAll();

    @Query("SELECT * FROM project WHERE creatorId = :creatorId")
    LiveData<List<Project>> loadAllOf(long creatorId);

    @Query("SELECT COUNT(1) FROM project WHERE id = :id")
    boolean hasProject(long id);

    @Query("SELECT project.version FROM project WHERE id = :id")
    long getVersionOf(long id);

    @Update
    void update(ProjectPictureFileInfo pictureFile);
    @Delete
    void delete(ProjectPictureFileInfo pictureFile);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ProjectPictureFileInfo pictureFile);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(ProjectPictureFileInfo... pictureFiles);

    @Query("SELECT * FROM projectPictureFileInfo WHERE projectId = :projectId AND name = :name")
    LiveData<ProjectPictureFileInfo> load(Long projectId, String name);

    @Query("SELECT * FROM projectPictureFileInfo WHERE projectId = :projectId")
    LiveData<List<ProjectPictureFileInfo>> loadAllPicturesOf(Long projectId);

    @Query("SELECT * FROM projectPictureFileInfo")
    LiveData<List<ProjectPictureFileInfo>> loadAllPictures();
}
