package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.v4.util.Pair;

import com.playgroundideas.playgroundideas.datasource.local.DesignDao;
import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.datasource.local.UserDao;
import com.playgroundideas.playgroundideas.datasource.remote.DesignWebservice;
import com.playgroundideas.playgroundideas.datasource.remote.NetworkAccess;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.model.FavouritedDesign;
import com.playgroundideas.playgroundideas.model.FileInfo;
import com.playgroundideas.playgroundideas.model.User;

import java.io.IOException;
import java.io.InputStream;
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

    public LiveData<List<Pair<Design, Boolean>>> getAllOf(final User user) {
        refreshAllDesigns();
        LiveData<List<Design>> designsLiveData = designDao.loadAll();
        LiveData<List<Pair<Design, Boolean>>> enrichedDesignsLiveData = Transformations.map(designsLiveData, new Function<List<Design>, List<Pair<Design, Boolean>>>() {
            @Override
            public List<Pair<Design, Boolean>> apply(List<Design> designs) {
                List<Pair<Design, Boolean>> list = new LinkedList<Pair<Design, Boolean>>();
                for(Design design : designs) {
                    list.add(new Pair<Design, Boolean>(design, isFavouriteOf(design, user)));
                }
                return list;
            }
        });
        return enrichedDesignsLiveData;
    }

    public LiveData<List<Pair<Design, Boolean>>> getFavouritesOf(final User user) {
        refreshAllDesigns();
        LiveData<List<Design>> favouriteDesigns = designDao.loadFavouritesOf(user.getId());
        LiveData<List<Pair<Design, Boolean>>> enrichedDesignsLiveData = Transformations.map(favouriteDesigns, new Function<List<Design>, List<Pair<Design, Boolean>>>() {
            @Override
            public List<Pair<Design, Boolean>> apply(List<Design> designs) {
                List<Pair<Design, Boolean>> list = new LinkedList<Pair<Design, Boolean>>();
                for(Design design : designs) {
                    list.add(new Pair<Design, Boolean>(design, isFavouriteOf(design, user)));
                }
                return list;
            }
        });
        return enrichedDesignsLiveData;
    }

    public boolean isFavouriteOf(Design design, User user) {
        return designDao.isFavouriteOf(design.getId(), user.getId());
    }

    private void storeImage(Design design, InputStream image) {
        try {
            FileInfo fileInfo = FileStorage.writeDesignImageFile("design_image" + design.getId().toString() + ".png", image);
            design.setImageInfo(fileInfo);
            designDao.insert(design);
        } catch (IOException ioe) {

        }
    }

    private void storeGuide(Design design, InputStream guide) {
        try {
            FileInfo fileInfo = FileStorage.writeDesignImageFile("design_guide" + design.getId().toString() + ".pdf", guide);
            design.setImageInfo(fileInfo);
            designDao.insert(design);
        } catch (IOException ioe) {

        }
    }

    public void addFavourite(Design design, User user) {
        FavouritedDesign relation = new FavouritedDesign(user.getId(), design.getId());
        user.increaseVersion();
        userDao.update(user);
        designDao.addFavourite(relation);
    }

    public void removeFavourite(Design design, User user) {
        user.increaseVersion();
        userDao.update(user);
        designDao.removeFavourite(user.getId(), design.getId());
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
                                        final Design design = response.body();
                                        // Update the database. The LiveData will automatically refresh so
                                        // we don't need to do anything else here besides updating the database
                                        if (localVersion == 0) {
                                            designDao.insert(design);
                                        } else {
                                            designDao.update(design);
                                        }
                                        final long designId = design.getId();

                                        // load image of the design
                                        webservice.getImageOf(designId).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if(response.isSuccessful()) {
                                                    storeImage(design, response.body().byteStream());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                                            }
                                        });

                                        // load pdf guide of the design
                                        webservice.getPdfOf(designId).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if(response.isSuccessful()) {
                                                    storeGuide(design, response.body().byteStream());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

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
