package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.playgroundideas.playgroundideas.model.ProjectPictureFile;

import java.util.List;

/**
 * Created by Ferdinand on 13/09/2017.
 */

@Dao
public interface ProjectPictureFileDao {

    @Update
    void update(ProjectPictureFile pictureFile);
    @Delete
    void delete(ProjectPictureFile pictureFile);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ProjectPictureFile pictureFile);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(ProjectPictureFile... pictureFiles);

    @Query("SELECT * FROM projectPictureFile WHERE projectId = :projectId AND name = :filename")
    LiveData<ProjectPictureFile> load(Long projectId, String filename);

    @Query("SELECT * FROM projectPictureFile WHERE projectId = :projectId")
    LiveData<List<ProjectPictureFile>> loadALLOf(Long projectId);

    @Query("SELECT COUNT(1) FROM projectPictureFile WHERE id = :projectId AND name = :filename")
    boolean has(Long projectId, String filename);
}
