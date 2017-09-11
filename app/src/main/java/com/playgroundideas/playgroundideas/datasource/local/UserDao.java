package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.playgroundideas.playgroundideas.domain.User;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Dao
public interface UserDao {
    @Update
    public void update(User user);
    @Delete
    public void delete(User user);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertUser(User user);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertUsers(User... users);

    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> load(long id);

    @Query("SELECT COUNT(1) FROM user WHERE id = :id")
    boolean hasUser(long id);
}
