package com.testtarget.ui.repo;

import android.view.View;
import com.testtarget.network.model.TopWeekly;
import com.testtarget.network.repository.Repository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kuljeetsingh on 12/6/18.
 */

public class TopRepoListPresenter {
    private Repository repository;
    protected TopRepoListViewModel viewModel;
    private final String LANGUAGE = "java";
    private final String SINCE_TYPE = "weekly";

    public TopRepoListPresenter(TopRepoListViewModel viewModel) {
        this.viewModel = viewModel;
        repository = Repository.getRepository();
        getTopWeeklyRepo();
        setupListErrorVisibility(View.GONE, View.GONE,View.VISIBLE);
        getTopWeeklyRepo();
    }

    private void setupListErrorVisibility(int errorVisibility, int listVisibility,int loadingVisibility) {
        viewModel.errorMessageVisibility.setValue(errorVisibility);
        viewModel.repoListVisibility.setValue(listVisibility);
        viewModel.loadingVisibility.setValue(loadingVisibility);
    }

    public void getTopWeeklyRepo() {
        repository.getTopWeeklyRepo(LANGUAGE, SINCE_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRepoListSuccess, this::onNetworkFailure);
    }

    private void onNetworkFailure(Throwable throwable) {
        //you can handle network exceptions here like retry dialog show error messages
        setupListErrorVisibility(View.VISIBLE, View.GONE,View.GONE);
        viewModel.errorMessage.setValue("Network error. Please try again later.");
    }

    private void onRepoListSuccess(List<TopWeekly> topWeeklies) {
        if (topWeeklies != null && topWeeklies.size() > 0) {
            setupListErrorVisibility(View.GONE, View.VISIBLE,View.GONE);
            viewModel.repoListData.postValue(topWeeklies);
            return;
        }
        setupListErrorVisibility(View.VISIBLE, View.GONE,View.GONE);
        viewModel.errorMessage.setValue("No list available.");
    }
}
