package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.playgroundideas.playgroundideas.model.User;

import java.util.List;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Dao
public interface UserDao {
    @Update
    public void update(User user);
    @Delete
    public void delete(User user);
    @Insert(onConflict = OnConflictStrategy.FAIL)
    public long insert(User user);

    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> load(long id);

    @Query("SELECT * FROM user")
    LiveData<List<User>> loadAll();

    @Query("SELECT COUNT(1) FROM user WHERE id = :id")
    boolean hasUserWith(long id);

    @Query("SELECT user.version FROM user WHERE id = :id")
    long getVersionOf(long id);
}
