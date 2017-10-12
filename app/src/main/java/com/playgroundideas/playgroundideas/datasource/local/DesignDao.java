package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.FavouritedDesign;

import java.util.List;

/**
 * Created by Ferdinand on 11/09/2017.
 */

@Dao
public interface DesignDao {

    @Update
    public void update(Design design);
    @Delete
    public void delete(Design design);
    @Insert(onConflict = OnConflictStrategy.FAIL)
    public long insert(Design design);

    @Query("SELECT * FROM design WHERE id = :id")
    LiveData<Design> load(long id);

    @Query("SELECT * FROM design")
    LiveData<List<Design>> loadAll();

    @Query("SELECT * FROM design WHERE creatorId = :creatorId")
    LiveData<List<Design>> loadAllOf(long creatorId);

    @Query("SELECT design.* FROM design INNER JOIN favouritedDesign ON favouritedDesign.userId =  :userId")
    LiveData<List<Design>> loadFavouritesOf(long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addFavourite(FavouritedDesign favourite);

    @Delete
    void removeFavourite(FavouritedDesign favourite);

    @Query("DELETE FROM favouritedDesign WHERE userId = :id")
    void removeAllFavouritesOf(long id);

    @Query("SELECT COUNT(1) FROM design WHERE id = :id")
    boolean hasDesign(long id);

    @Query("SELECT design.version FROM design WHERE id = :id")
    long getVersionOf(long id);
}
