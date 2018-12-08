package com.testtarget.ui.repo

import android.view.View
import com.testtarget.network.model.TopWeekly
import com.testtarget.network.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by kuljeetsingh on 12/6/18.
 */

class TopRepoListPresenter(protected var viewModel: TopRepoListViewModel, private val repository: Repository) {
    private val LANGUAGE = "java"
    private val SINCE_TYPE = "weekly"

    init {
        setupListErrorVisibility(View.GONE, View.GONE, View.VISIBLE)
    }

    private fun setupListErrorVisibility(errorVisibility: Int, listVisibility: Int, loadingVisibility: Int) {
        viewModel.errorMessageVisibility.value = errorVisibility
        viewModel.repoListVisibility.value = listVisibility
        viewModel.loadingVisibility.value = loadingVisibility
    }

    fun getTopWeeklyRepo() {
        repository.getTopWeeklyRepo(LANGUAGE, SINCE_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.onRepoListSuccess(it) }, { this.onNetworkFailure(it) })
    }

    private fun onNetworkFailure(throwable: Throwable) {
        //you can handle network exceptions here like retry dialog show error messages
        setupListErrorVisibility(View.VISIBLE, View.GONE, View.GONE)
        viewModel.errorMessage.value = "Network error. Please try again later."
    }

    private fun onRepoListSuccess(topWeeklies: List<TopWeekly>?) {
        if (topWeeklies != null && topWeeklies.isNotEmpty()) {
            setupListErrorVisibility(View.GONE, View.VISIBLE, View.GONE)
            viewModel.repoListData.postValue(topWeeklies)
            return
        }
        setupListErrorVisibility(View.VISIBLE, View.GONE, View.GONE)
        viewModel.errorMessage.value = "No list available."
    }
}
