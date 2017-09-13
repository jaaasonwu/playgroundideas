package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.playgroundideas.playgroundideas.model.FavouritedDesignsPerUser;

import java.util.List;

/**
 * Created by Ferdinand on 13/09/2017.
 */

@Dao
public interface FavouritedDesignsPerUserDao {

    @Delete
    public void delete(FavouritedDesignsPerUser favouritedDesignsPerUser);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(FavouritedDesignsPerUser favouritedDesignsPerUser);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insert(FavouritedDesignsPerUser... favouritedDesignsPerUsers);

    @Query("SELECT * FROM favouritedDesignsPerUser WHERE userId = :userId")
    LiveData<List<FavouritedDesignsPerUser>> loadAllOf(long userId);

    @Query("SELECT COUNT(1) FROM favouritedDesignsPerUser WHERE designId = :designId AND userId = :userId")
    boolean isFavouritedBy(long designId, long userId);
}
