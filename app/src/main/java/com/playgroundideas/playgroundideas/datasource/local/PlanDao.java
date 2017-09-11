package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.playgroundideas.playgroundideas.domain.Plan;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Dao
public interface PlanDao {
    @Update
    public void update(Plan plan);
    @Delete
    public void delete(Plan plan);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertUser(Plan plan);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertUsers(Plan... plans);

    @Query("SELECT * FROM plan WHERE id = :id")
    LiveData<Plan> load(long id);

    @Query("SELECT COUNT(1) FROM plan WHERE id = :id")
    boolean hasPlan(long id);
}
