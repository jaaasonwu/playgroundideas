package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.playgroundideas.playgroundideas.datasource.local.DesignDao;
import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.datasource.local.UserDao;
import com.playgroundideas.playgroundideas.datasource.remote.DesignWebservice;
import com.playgroundideas.playgroundideas.datasource.remote.NetworkAccess;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.DesignPictureFileInfo;
import com.playgroundideas.playgroundideas.model.FavouritedDesign;
import com.playgroundideas.playgroundideas.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Singleton
public class DesignRepository {

    private final DesignWebservice webservice;
    private final DesignDao designDao;
    private final UserDao userDao;
    private final Executor executor;
    private final NetworkAccess networkAccess;

    @Inject
    public DesignRepository(DesignWebservice webservice, DesignDao designDao, UserDao userDao, Executor executor, NetworkAccess networkAccess) {
        this.webservice = webservice;
        this.designDao = designDao;
        this.userDao = userDao;
        this.executor = executor;
        this.networkAccess = networkAccess;
    }

    public LiveData<Design> get(Long id) {
        refreshDesign(id);
        return designDao.load(id);
    }

    public void createDesign(Design design) {
        designDao.insert(design);

    }

    public void updateDesign(Design design) {
        design.increaseVersion();
        designDao.update(design);
    }

    public void deleteDesign(Design design) {
        designDao.delete(design);
    }

    public LiveData<List<Design>> getAllCreatedBy(Long creatorId) {
        refreshAllDesigns();
        LiveData<List<Design>> designsLiveData = designDao.loadAllOf(creatorId);
        return designsLiveData;
    }

    public LiveData<List<Design>> getAll() {
        refreshAllDesigns();
        LiveData<List<Design>> designsLiveData = designDao.loadAll();
        return designsLiveData;
    }

    public LiveData<List<Design>> getFavouritesOf(Long userId) {
        refreshAllDesigns();
        LiveData<List<Design>> favouriteDesigns = designDao.loadFavouritesOf(userId);
        return favouriteDesigns;
    }

    public LiveData<List<DesignPictureFileInfo>> getPicturesOf(Long designId) {
        refreshAllDesigns();
        LiveData<List<DesignPictureFileInfo>> pictures = designDao.loadAllPicturesOf(designId);
        return pictures;
    }

    public LiveData<Map<Long, List<DesignPictureFileInfo>>> getAllPicturesPerDesign() {
        refreshAllDesigns();
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

    private void storeImage(Long designId, String imageId, InputStream image) {
        DesignPictureFileInfo info = new DesignPictureFileInfo(imageId, designId);
        try {
            FileStorage.writeDesignPictureFile(info, image);
            designDao.insert(info);
        } catch (IOException ioe) {

        }
    }

    public void addFavourite(Design design, User user) {
        FavouritedDesign relation = new FavouritedDesign(user.getId(), design.getId());
        user.increaseVersion();
        userDao.update(user);
        designDao.addFavourite(relation);
    }

    public void removeFavourite(FavouritedDesign favouritedDesign) {
        User user = userDao.load(favouritedDesign.getUserId()).getValue();
        user.increaseVersion();
        userDao.update(user);
        designDao.removeFavourite(favouritedDesign);
    }

    private void refreshDesign(final Long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if there is a network connection
                if(networkAccess.isNetworkAvailable()) {

                    webservice.getVersion(id).enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            // check if the date has changed on the server using a version number
                            final long remoteVersion = response.body();
                            final long localVersion = designDao.getVersionOf(id);

                            if (remoteVersion > localVersion) {
                                // refresh the local data
                                // retrieve the data from the remote data source
                                webservice.getDesign(id).enqueue(new Callback<Design>() {
                                    @Override
                                    public void onResponse(Call<Design> call, Response<Design> response) {
                                        //create new object from response.body
                                        Design design = response.body();
                                        // Update the database. The LiveData will automatically refresh so
                                        // we don't need to do anything else here besides updating the database
                                        if (localVersion == 0) {
                                            designDao.insert(design);
                                        } else {
                                            designDao.update(design);
                                        }
                                        final long designId = design.getId();

                                        // load images of the design
                                        webservice.getImageIdsOf(designId).enqueue(new Callback<List<String>>() {
                                            @Override
                                            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                                if(response.isSuccessful()) {
                                                    for(final String imageId : response.body()) {
                                                        webservice.getImage(designId, imageId).enqueue(new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                if(response.isSuccessful()) {
                                                                    storeImage(designId, imageId, response.body().byteStream());
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<String>> call, Throwable throwable) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<Design> call, Throwable throwable) {
                                        // do nothing
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable throwable) {
                            // do nothing
                        }
                    });
                }
            }
        });
    }

    private void refreshAllDesigns() {
        // running in a background thread
        // check if there is a network connection
        if(networkAccess.isNetworkAvailable()) {

            // check if any data has changed on the server
            webservice.getVersionOfAll().enqueue(new Callback<Map<Long, Long>>() {
                @Override
                public void onResponse(Call<Map<Long, Long>> call, Response<Map<Long, Long>> response) {
                    for (Map.Entry<Long, Long> remote : response.body().entrySet()) {
                        final long localVersion = designDao.getVersionOf(id);
                        if (remote.getValue() > localVersion) {
                            // refresh if data has changed
                            refreshDesign(remote.getKey());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map<Long, Long>> call, Throwable throwable) {
                    // do nothing
                }
            });
        }
    }
}
