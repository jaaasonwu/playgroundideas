package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.ManualRepository;
import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.model.Manual;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class ManualsListViewModel extends ViewModel {

    private LiveData<List<Manual>> allManuals;
    private LiveData<List<Manual>> downloadedManuals = Transformations.map(allManuals, new Function<List<Manual>, List<Manual>>() {
        @Override
        public List<Manual> apply(List<Manual> manuals) {
            List<Manual> downloadedManuals = new LinkedList<>();
            for(Manual m : manuals) {
                if(FileStorage.isDownloaded(m.getPdfInfo())) {
                    downloadedManuals.add(m);
                }
            }
            return downloadedManuals;
        }
    });
    private ManualRepository manualRepository;

    @Inject
    public ManualsListViewModel(ManualRepository manualRepository) {
        this.manualRepository = manualRepository;
    }

    public LiveData<List<Manual>> getAllManuals() {
        if (allManuals == null) {
            allManuals = manualRepository.getAll();
        }
        return allManuals;
    }

    public LiveData<List<Manual>> getDownloadedManuals() {
        if (allManuals == null) {
            allManuals = manualRepository.getAll();
        }
        return downloadedManuals;
    }
}
