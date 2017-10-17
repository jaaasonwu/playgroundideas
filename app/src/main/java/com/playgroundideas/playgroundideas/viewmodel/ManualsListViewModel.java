package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.repository.ManualRepository;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualChapter;

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
                if(m.isPDFDownloaded()) {
                    downloadedManuals.add(m);
                }
            }
            return downloadedManuals;
        }
    });
    private LiveData<List<Manual>> notDownloadedManuals = Transformations.map(allManuals, new Function<List<Manual>, List<Manual>>() {
        @Override
        public List<Manual> apply(List<Manual> manuals) {
            List<Manual> downloadedManuals = new LinkedList<>();
            for(Manual m : manuals) {
                if(!m.isPDFDownloaded()) {
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

    public LiveData<List<Manual>> getNotDownloadedManuals() {
        if (allManuals == null) {
            allManuals = manualRepository.getAll();
        }
        return notDownloadedManuals;
    }

    public void makeDownloaded(Manual manual, boolean downloaded) {
        if(manual.isPDFDownloaded()) {
            if(!downloaded) {
                manualRepository.removeDownloadedManual(manual);
            }
        } else {
            if(downloaded) {
                manualRepository.downloadManual(manual);
            }
        }
    }

    public LiveData<List<ManualChapter>> getChaptersOf(Manual manual) {
        return manualRepository.getManualChaptersOf(manual.getId());
    }

    public LiveData<List<ManualChapter>> getChapters() {
        return manualRepository.getManualChapters();
    }
}
