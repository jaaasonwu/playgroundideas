package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.playgroundideas.playgroundideas.datasource.local.DesignDao;
import com.playgroundideas.playgroundideas.datasource.remote.DesignWebservice;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.DesignPictureFileInfo;
import com.playgroundideas.playgroundideas.model.FavouritedDesignsPerUser;
import com.playgroundideas.playgroundideas.model.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Singleton
public class DesignRepository {

    private final DesignWebservice webservice;
    private final DesignDao designDao;
    private final Executor executor;

    @Inject
    public DesignRepository(DesignWebservice webservice, DesignDao designDao, Executor executor) {
        this.webservice = webservice;
        this.designDao = designDao;
        this.executor = executor;
    }

    public LiveData<Design> get(Long id) {
        refreshDesign(id);
        return designDao.load(id);
    }

    public void createDesign(Design design) {
        designDao.insert(design);
    }

    public void updateDesign(Design design) {
        designDao.update(design);
    }

    public void deleteDesign(Design design) {
        designDao.delete(design);
    }

    public LiveData<List<Design>> getAllCreatedBy(Long creatorId) {
        LiveData<List<Design>> designsLiveData = designDao.loadAllOf(creatorId);
        return designsLiveData;
    }

    public LiveData<List<Design>> getAll() {
        LiveData<List<Design>> designsLiveData = designDao.loadAll();
        return designsLiveData;
    }

    public LiveData<List<Design>> getFavouritesOf(Long userId) {
        LiveData<List<Design>> favouriteDesigns = designDao.loadFavouritesOf(userId);
        return favouriteDesigns;
    }

    public LiveData<List<DesignPictureFileInfo>> getPicturesOf(Long designId) {
        LiveData<List<DesignPictureFileInfo>> pictures = designDao.loadAllPicturesOf(designId);
        return pictures;
    }

    public LiveData<Map<Long, List<DesignPictureFileInfo>>> getAllPicturesPerDesign() {
        LiveData<List<DesignPictureFileInfo>> allPictures = designDao.loadAllPictures();
        LiveData<Map<Long, List<DesignPictureFileInfo>>> picturesPerDesign = Transformations.map(allPictures, new Function<List<DesignPictureFileInfo>, Map<Long, List<DesignPictureFileInfo>>>() {
            @Override
            public Map<Long, List<DesignPictureFileInfo>> apply(List<DesignPictureFileInfo> designPictureFileInfos) {
                Map<Long, List<DesignPictureFileInfo>> map = new HashMap<Long, List<DesignPictureFileInfo>>();
                for (DesignPictureFileInfo info : designPictureFileInfos) {
                    if (!map.containsKey(info.getDesignId())) {
                        map.put(info.getDesignId(), new LinkedList<DesignPictureFileInfo>());
                    }
                    map.get(info.getDesignId()).add(info);
                }
                return map;
            }
        });
        return picturesPerDesign;
    }

    public void addImage(DesignPictureFileInfo pictureFileInfo) {
        designDao.insert(pictureFileInfo);
    }

    public void removeImage(DesignPictureFileInfo pictureFileInfo) {
        designDao.delete(pictureFileInfo);
    }

    public void addFavourite(Design design, User user) {
        FavouritedDesignsPerUser relation = new FavouritedDesignsPerUser(user.getId(), design.getId());
        designDao.addFavourite(relation);
    }

    public void removeFavourite(Design design, User user) {
        designDao.removeFavourite(design.getId(), user.getId());
    }

    private void refreshDesign(final Long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if user was fetched recently
                boolean designExists = designDao.hasDesign(id);
                if (!designExists) {
                    // TODO implement API response handling (see UserRepository)
                }
            }
        });
    }
}
