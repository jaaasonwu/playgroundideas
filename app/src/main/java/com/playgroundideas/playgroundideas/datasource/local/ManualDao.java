package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.playgroundideas.playgroundideas.model.Manual;

/**
 * Created by Ferdinand on 11/09/2017.
 */

@Dao
public interface ManualDao {

    @Update
    public void update(Manual manual);
    @Delete
    public void delete(Manual manual);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertManual(Manual manual);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertManuals(Manual... manuals);

    @Query("SELECT * FROM manual WHERE id = :id")
    LiveData<Manual> load(long id);

    @Query("SELECT COUNT(1) FROM manual WHERE id = :id")
    boolean hasManual(long id);
}
