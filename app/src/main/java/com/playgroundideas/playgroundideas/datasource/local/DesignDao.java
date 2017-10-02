package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.DesignPictureFileInfo;
import com.playgroundideas.playgroundideas.model.FavouritedDesignsPerUser;

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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Design design);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insert(Design... designs);

    @Query("SELECT * FROM design WHERE id = :id")
    LiveData<Design> load(long id);

    @Query("SELECT * FROM design")
    LiveData<List<Design>> loadAll();

    @Query("SELECT * FROM design WHERE creatorId = :creatorId")
    LiveData<List<Design>> loadAllOf(long creatorId);

    @Query("SELECT design.* FROM design INNER JOIN favouritedDesignsPerUser ON favouritedDesignsPerUser.userId =  :userId")
    LiveData<List<Design>> loadFavouritesOf(long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addFavourite(FavouritedDesignsPerUser favourite);

    @Query("DELETE FROM favouritedDesignsPerUser WHERE userId = :userId AND designId = :designId")
    void removeFavourite(long designId, long userId);


    @Query("SELECT COUNT(1) FROM design WHERE id = :id")
    boolean hasDesign(long id);

    @Update
    void update(DesignPictureFileInfo pictureFile);
    @Delete
    void delete(DesignPictureFileInfo pictureFile);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(DesignPictureFileInfo pictureFile);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(DesignPictureFileInfo... pictureFiles);

    @Query("SELECT * FROM designPictureFileInfo WHERE designId = :designId AND name = :filename")
    LiveData<DesignPictureFileInfo> load(Long designId, String filename);

    @Query("SELECT * FROM designPictureFileInfo WHERE designId = :designId")
    LiveData<List<DesignPictureFileInfo>> loadAllPicturesOf(Long designId);

    @Query("SELECT * FROM designPictureFileInfo")
    LiveData<List<DesignPictureFileInfo>> loadAllPictures();

    @Query("SELECT COUNT(1) FROM designPictureFileInfo WHERE designId = :designId AND name = :filename")
    boolean hasPicture(Long designId, String filename);
}
