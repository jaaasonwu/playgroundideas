package com.playgroundideas.playgroundideas.plans;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.PlanRepository;
import com.playgroundideas.playgroundideas.domain.Plan;

/**
 * Created by Ferdinand on 9/09/2017.
 */

public class PlanViewModel extends ViewModel {
    private LiveData<Plan> plan;
    private PlanRepository planRepository;

    public PlanViewModel(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public void init(long planId) {
        if (this.plan != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        plan = planRepository.getPlan(planId);
    }

    public LiveData<Plan> getPlan() {
        return this.plan;
    }
}
