package com.testtarget.ui.repo;

import android.support.annotation.VisibleForTesting;
import android.view.View;

import com.testtarget.network.model.TopWeekly;
import com.testtarget.network.repository.Repository;
import com.testtarget.utils.schedulers.SchedulerProvider;

import java.util.List;

/**
 * Created by kuljeetsingh on 12/6/18.
 */

public class TopRepoListPresenter {
    private Repository repository;
    protected TopRepoListViewModel viewModel;
    private final String LANGUAGE = "java";
    private final String SINCE_TYPE = "weekly";
    private  SchedulerProvider schedulerProvider;

    public TopRepoListPresenter(TopRepoListViewModel viewModel, Repository repository, SchedulerProvider schedulerProvider) {
        this.repository = repository;
        this.viewModel = viewModel;
        this.schedulerProvider = schedulerProvider;
        setupListErrorVisibility(View.GONE, View.GONE, View.VISIBLE);
    }

    @VisibleForTesting
    SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }



    private void setupListErrorVisibility(int errorVisibility, int listVisibility, int loadingVisibility) {

        viewModel.errorMessageVisibility.setValue(errorVisibility);
        viewModel.repoListVisibility.setValue(listVisibility);
        viewModel.loadingVisibility.setValue(loadingVisibility);
    }



    public void getTopWeeklyRepo() {
        repository.getTopWeeklyRepo(LANGUAGE, SINCE_TYPE)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::onRepoListSuccess, this::onNetworkFailure);
    }

    private void onNetworkFailure(Throwable throwable) {
        //you can handle network exceptions here like retry dialog show error messages
        setupListErrorVisibility(View.VISIBLE, View.GONE, View.GONE);
        viewModel.errorMessage.setValue("Network error. Please try again later.");
    }

    private void onRepoListSuccess(List<TopWeekly> topWeeklies) {
        if (topWeeklies != null && topWeeklies.size() > 0) {
            setupListErrorVisibility(View.GONE, View.VISIBLE, View.GONE);
            viewModel.repoListData.postValue(topWeeklies);
            return;
        }
        setupListErrorVisibility(View.VISIBLE, View.GONE, View.GONE);
        viewModel.errorMessage.setValue("No list available.");
    }
}
